/**
 * 
 */
package hunting.common.dao;

import hunting.common.pojo.HuntingGameRewardsRecord;
import hunting.common.pojoenum.RewardsEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author yunan.zheng
 * 
 */
@Service
public class RewardsRecordDao {

    @Resource
    public JdbcTemplate jdbcTemplate;

    public String INSERT = "INSERT INTO hunting_game_rewards_record (rewards_type, rewards_amount, operator, rewards_id, player_id, created_time, game_city) VALUES(?,?,?,?,?,?,?);";

    public String SELECT_COMMON = "SELECT  id, rewards_type, rewards_amount, operator, rewards_id, player_id, created_time, game_city   FROM hunting_game_rewards_record ";

    public String SELECT_AMOUNT = "SELECT  count(*) as amount   FROM hunting_game_rewards_record ";

    
    public int[] insert(List<HuntingGameRewardsRecord> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return null;
        }
        List<Object[]> insertParams = new ArrayList<Object[]>();
        for (HuntingGameRewardsRecord huntingGameRewardsRecord : datas) {
            List<Object> subParams = new ArrayList<Object>();
            subParams.add(huntingGameRewardsRecord.getRewardsType().name());
            subParams.add(huntingGameRewardsRecord.getRewardsAmount());
            subParams.add(huntingGameRewardsRecord.getOperator());
            subParams.add(huntingGameRewardsRecord.getRewardsId());
            subParams.add(huntingGameRewardsRecord.getPlayerId());
            subParams.add(huntingGameRewardsRecord.getCreatedTime());
            subParams.add(huntingGameRewardsRecord.getGameCity());
            insertParams.add(subParams.toArray());
        }
        return jdbcTemplate.batchUpdate(INSERT, insertParams);
    }

    public int amount(String createdTime, String operator, String playerId) {
        StringBuilder sbr = new StringBuilder(SELECT_AMOUNT);
        sbr.append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.hasLength(createdTime)) {
            sbr.append(" and created_time >= ?  and created_time <= ? ");
            params.add(createdTime + " 00:00:00");
            params.add(createdTime + " 23:59:59");
        }
        if (StringUtils.hasLength(operator)) {
            sbr.append(" and operator = ? ");
            params.add(operator);
        }
        if (StringUtils.hasLength(playerId)) {
            sbr.append(" and player_id = ? ");
            params.add(playerId);
        }

        return jdbcTemplate.queryForInt(sbr.toString(), params.toArray());
    }

    public List<HuntingGameRewardsRecord> listByCondition(String createdTime, String operator,
            String playerId,int limit,int offset) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.hasLength(createdTime)) {
            sbr.append(" and created_time >= ?  and created_time <= ? ");
            params.add(createdTime + " 00:00:00");
            params.add(createdTime + " 23:59:59");
        }
        if (StringUtils.hasLength(operator)) {
            sbr.append(" and operator = ? ");
            params.add(operator);
        }
        if (StringUtils.hasLength(playerId)) {
            sbr.append(" and player_id = ? ");
            params.add(playerId);
        }
        sbr.append(" limit ? offset ? ");
        params.add(limit);
        params.add(offset);
        
        return jdbcTemplate.query(sbr.toString(), params.toArray(),
                new ResultSetExtractor<List<HuntingGameRewardsRecord>>() {

                    @Override
                    public List<HuntingGameRewardsRecord> extractData(ResultSet rs)
                            throws SQLException, DataAccessException {
                        List<HuntingGameRewardsRecord> result = new ArrayList<HuntingGameRewardsRecord>();
                        while (rs.next()) {
                            HuntingGameRewardsRecord rr = new HuntingGameRewardsRecord();
                            rr.setId(rs.getLong("id"));
                            String rewardsTypeStr = rs.getString("rewards_type");
                            if (rewardsTypeStr.equals(RewardsEnum.DIAN_JUAN.name())) {
                                rr.setRewardsType(RewardsEnum.DIAN_JUAN);
                            } else if (rewardsTypeStr.equals(RewardsEnum.LU_DIAN.name())) {
                                rr.setRewardsType(RewardsEnum.LU_DIAN);
                            }
                            rr.setRewardsAmount(rs.getFloat("rewards_amount"));
                            rr.setOperator(rs.getString("operator"));
                            rr.setRewardsId(rs.getString("rewards_id"));
                            rr.setPlayerId(rs.getString("player_id"));
                            rr.setCreatedTime(rs.getDate("created_time"));
                            rr.setGameCity(rs.getString("game_city"));
                            result.add(rr);
                        }
                        return result;
                    }

                });
    }
}
