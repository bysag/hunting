/**
 * 
 */
package hunting.common.dao;

import hunting.common.pojo.HuntingGameBlackList;
import hunting.common.pojoenum.BlackListEnum;
import hunting.common.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yunan.zheng
 * 
 */
@Service
public class BlackListDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private String INSERT = "INSERT INTO hunting_game_blacklist ( player_id, TYPE, created_time, last_mod_time, operator, game_city)VALUES(?, ?, ?, ?, ?, ?);";

    private String UPDATE = "update hunting_game_blacklist set type=? ,last_mod_time=now() where player_id=?";

    private String SELECT_COMMON = " SELECT  id, player_id as playerId, type, created_time as createdTime, last_mod_time as lastModTime, operator, game_city as gameCity FROM hunting_game_blacklist  ";

    private String AMOUNT = "SELECT count(*) as amount from hunting_game_blacklist ";

    public int insert(List<HuntingGameBlackList> bls) {
        List<Object[]> insertParams = new ArrayList<Object[]>();
        for (HuntingGameBlackList bl : bls) {
            List<Object> param = new ArrayList<Object>();
            param.add(bl.getPlayerId());
            param.add(bl.getType().name());
            param.add(bl.getCreatedTime());
            param.add(bl.getLastModTime());
            param.add(bl.getOperator());
            param.add(bl.getGameCity());
            insertParams.add(param.toArray());
        }
        int[] res = jdbcTemplate.batchUpdate(INSERT, insertParams);
        return res.length;
    }

    public int amount(String gameCity, String playerId) {
        StringBuilder sbr = new StringBuilder(AMOUNT);
        List<Object> params = new ArrayList<Object>();
        sbr.append(" where type = ? ");
        params.add(BlackListEnum.VALID.name());
        if (StringUtils.hasLength(gameCity)) {
            sbr.append("and  gameCity = ?");
            params.add(gameCity);
        }
        if (StringUtils.hasLength(playerId)) {
            sbr.append("and  player_id like '%");
            sbr.append(playerId);
            sbr.append("%'");
        }
        return jdbcTemplate.queryForInt(sbr.toString(), params.toArray());
    }

    public int delete(String playerId) {
        return jdbcTemplate.update(UPDATE, new Object[] { BlackListEnum.INVALID.name(), playerId });
    }

    public List<HuntingGameBlackList> list(String gameCity, String playerId, int limit, int offset) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append(" where type = ? ");
        params.add(BlackListEnum.VALID.name());
        if (StringUtils.hasLength(gameCity)) {
            sbr.append("and  gameCity = ?");
            params.add(gameCity);
        }
        if (StringUtils.hasLength(playerId)) {
            sbr.append("and  player_id like '%");
            sbr.append(playerId);
            sbr.append("%'");
        }
        sbr.append(" limit ? offset ? ");
        params.add(limit);
        params.add(offset);
        List<HuntingGameBlackList> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new blackListResult());
        return res;
    }

    public List<HuntingGameBlackList> incrementList(long lastModTime, int limit) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append(" where last_mod_time > ? ");
        params.add(DateUtil.format(new Date(lastModTime), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        sbr.append(" order by id desc limit ?");
        params.add(limit);
        List<HuntingGameBlackList> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new blackListResult());
        return res;
    }

    class blackListResult implements ResultSetExtractor<List<HuntingGameBlackList>> {

        @Override
        public List<HuntingGameBlackList> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            List<HuntingGameBlackList> res = new ArrayList<HuntingGameBlackList>();
            while (rs.next()) {
                HuntingGameBlackList bl = new HuntingGameBlackList();
                bl.setId(rs.getLong("id"));
                bl.setGameCity(rs.getString("gameCity"));
                bl.setLastModTime(rs.getTimestamp("lastModTime"));
                bl.setOperator(rs.getString("operator"));
                bl.setPlayerId(rs.getString("playerId"));
                String type = rs.getString("type");
                if (type.equals(BlackListEnum.VALID.name())) {
                    bl.setType(BlackListEnum.VALID);
                }else if (type.equals(BlackListEnum.INVALID.name())){
                    bl.setType(BlackListEnum.INVALID);
                }
                bl.setCreatedTime(rs.getTimestamp("createdTime"));
                res.add(bl);
            }
            return res;
        }
    }
}
