/**
 * 
 */
package hunting.common.dao;

import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojoenum.GameInfoEnum;
import hunting.common.utils.DateUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author yunan.zheng
 * 
 */
@Service
public class GameInfoDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private String SELECT_COMMON = "SELECT id, game_type, game_city, game_logo_name, game_logo_uri, game_grab_logo_distance, game_hide_time, game_hide_time_invalid, game_start_date, game_end_date, game_start_time, game_end_time, game_center_longitude, game_center_latitude, game_center_address, game_center_radius, game_logo_put_in_point_longitude, game_logo_put_in_point_latitude, game_logo_display_radius, game_rob_gold_times, game_max_move_speed, game_max_get_speed, game_share_text_template, created_time, last_mod_time, operator,game_logo_put_in_point_address FROM hunting_game_info ";

    private String INSERT = "INSERT INTO hunting_game_info (game_type, game_city, game_logo_name,  game_logo_uri,"
            + " game_grab_logo_distance, game_hide_time, game_hide_time_invalid, game_start_date, game_end_date, game_start_time, game_end_time, game_center_longitude, game_center_latitude, game_center_address,   game_center_radius, game_logo_put_in_point_longitude, game_logo_put_in_point_latitude, game_logo_display_radius, game_rob_gold_times,  game_max_move_speed, game_max_get_speed, game_share_text_template, created_time, last_mod_time, operator,game_logo_put_in_point_address) VALUES( ?, ?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?);";

    private String UPDATE = " UPDATE hunting_game_info  SET game_type = ? , game_city = ? , game_logo_name = ? , game_logo_uri = ? , game_grab_logo_distance = ? , game_hide_time = ? , game_hide_time_invalid = ? , game_start_date = ? , game_end_date = ?, game_start_time =? , game_end_time = ? , game_center_longitude = ? , game_center_latitude = ? , game_center_address = ?, game_center_radius = ? , game_logo_put_in_point_longitude = ? , game_logo_put_in_point_latitude =? , game_logo_display_radius = ? , game_rob_gold_times = ? , game_max_move_speed = ? , game_max_get_speed = ? , game_share_text_template = ? ,     last_mod_time = ? , operator = ? , game_logo_put_in_point_address = ?  WHERE id = ? ;";

    public HuntingGameInfo getCurrentGameInfo(String gameCity) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append("where 1=1 ");
        if (StringUtils.hasLength(gameCity)) {
            sbr.append(" and game_city = ?");
            params.add(gameCity);
        }
        sbr.append(" and game_start_time <= ?");
        sbr.append(" and game_end_time >= ?");
        sbr.append(" order by game_start_time desc ");
        String timeStr = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        params.add(timeStr);
        params.add(timeStr);

        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return null;
        }
        return res.get(0);
    }

    public List<HuntingGameInfo> getDateGameInfo(String startDate,String endDate) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append(" where game_start_time >= ? and game_start_time <=? ");
        params.add(startDate);
        params.add(endDate);
        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());
        return res;
    }
    
    public  List<HuntingGameInfo> getCurrentGameInfoList() {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append("where 1=1 ");
        sbr.append(" and game_start_time <= ?");
        sbr.append(" and game_end_time >= ?");
        sbr.append(" order by game_start_time desc ");
        String timeStr = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        params.add(timeStr);
        params.add(timeStr);
        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return null;
        }
        return res;
    }
    
    public List<HuntingGameInfo> getGameInfoList(String gameCity){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append("where game_city = ? ");
        sbr.append(" order by game_start_time desc ");
        return jdbcTemplate.query(sbr.toString(),new Object[]{gameCity},
                new GameInfoResultSetExtractor());
    }
    
    public HuntingGameInfo getLastestGameInfo(String gameCity) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append("where 1=1 ");
        if (StringUtils.hasLength(gameCity)) {
            sbr.append(" and game_city = ?");
            params.add(gameCity);
        }
        sbr.append(" and game_start_time >= ?");
        sbr.append(" order by game_start_time asc limit 1; ");
        String timeStr = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        params.add(timeStr);

        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return null;
        }

        return res.get(0);
    }
    
    public HuntingGameInfo getGameInfo(long gameInfoId) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append("where id = ? ");
        params.add(gameInfoId);
        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return null;
        }

        return res.get(0);
    }

    public HuntingGameInfo getBeforeGameInfo(String gameCity) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append("where 1=1 ");
        if (StringUtils.hasLength(gameCity)) {
            sbr.append(" and game_city = ?");
            params.add(gameCity);
        }
        sbr.append(" and game_end_time <= ?");
        sbr.append(" order by game_end_time desc limit 1; ");
        String timeStr = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        params.add(timeStr);

        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return null;
        }

        return res.get(0);
    }

    public int insert(final HuntingGameInfo gi) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public java.sql.PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                int i = 0;
                java.sql.PreparedStatement ps = conn
                        .prepareStatement(INSERT, new String[] { "id" });
                ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(++i, gi.getGameType().name());
                ps.setString(++i, gi.getGameCity());
                ps.setString(++i, gi.getGameLogoName());
                ps.setString(++i, gi.getGameLogoUri());
                ps.setLong(++i, gi.getGameGrabLogoDistance());
                ps.setLong(++i, gi.getGameHideTime());
                ps.setLong(++i, gi.getGameHideTimeInvalid());
                ps.setDate(++i, new java.sql.Date(gi.getGameStartDate().getTime()));
                ps.setDate(++i, new java.sql.Date(gi.getGameEndDate().getTime()));
                ps.setTimestamp(++i, new Timestamp(gi.getGameStartTime().getTime()));
                ps.setTimestamp(++i, new Timestamp(gi.getGameEndTime().getTime()));
                ps.setString(++i, gi.getGameCenterLongitude());
                ps.setString(++i, gi.getGameCenterLatitude());
                ps.setString(++i, gi.getGameCenterAddress());
                ps.setString(++i, gi.getGameCenterRadius());
                ps.setString(++i, gi.getGameLogoPutInPointLongitude());
                ps.setString(++i, gi.getGameLogoPutInPointLatitude());
                ps.setString(++i, gi.getGameLogoDisplayRadius());
                ps.setInt(++i, gi.getGameRobGoldTimes());
                ps.setLong(++i, gi.getGameMaxGetSpeed());
                ps.setLong(++i, gi.getGameMaxMoveSpeed());
                ps.setString(++i, gi.getGameShareTextTemplate());
                ps.setTimestamp(++i, new Timestamp(gi.getCreatedTime().getTime()));
                ps.setTimestamp(++i, new Timestamp(gi.getLastModTime().getTime()));
                ps.setString(++i, gi.getOperator());
                ps.setString(++i, gi.getGameLogoPutInPointAddress());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int updateGameInfo(HuntingGameInfo gi) {
        int result = jdbcTemplate.update(
                UPDATE,
                new Object[] { gi.getGameType().name(), gi.getGameCity(), gi.getGameLogoName(),
                        gi.getGameLogoUri(), gi.getGameGrabLogoDistance(), gi.getGameHideTime(),
                        gi.getGameHideTimeInvalid(), gi.getGameStartDate(), gi.getGameEndDate(),
                        gi.getGameStartTime(), gi.getGameEndTime(), gi.getGameCenterLongitude(),
                        gi.getGameCenterLatitude(), gi.getGameCenterAddress(),
                        gi.getGameCenterRadius(), gi.getGameLogoPutInPointLongitude(),
                        gi.getGameLogoPutInPointLatitude(), gi.getGameLogoDisplayRadius(),
                        gi.getGameRobGoldTimes(), gi.getGameMaxGetSpeed(),
                        gi.getGameMaxMoveSpeed(), gi.getGameShareTextTemplate(),
                        gi.getLastModTime(), gi.getOperator(), gi.getGameLogoPutInPointAddress(),
                        gi.getId() });
        return result;

    }

    public int dateValidate(String gameCity, String startTime) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        List<Object> params = new ArrayList<Object>();
        sbr.append(" where game_city = ?");
        params.add(gameCity);
        sbr.append(" and game_start_time >= ?");
        params.add(startTime);

        List<HuntingGameInfo> res = jdbcTemplate.query(sbr.toString(), params.toArray(),
                new GameInfoResultSetExtractor());

        if (CollectionUtils.isEmpty(res)) {
            return 0;
        }

        return res.size();
    }

    class GameInfoResultSetExtractor implements ResultSetExtractor<List<HuntingGameInfo>> {

        @Override
        public List<HuntingGameInfo> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            List<HuntingGameInfo> hgiList = new ArrayList<HuntingGameInfo>();
            while (rs.next()) {
                HuntingGameInfo hgi = new HuntingGameInfo();
                hgi.setId(rs.getLong("id"));
                hgi.setCreatedTime(rs.getDate("created_time"));
                hgi.setGameCity(rs.getString("game_city"));
                String gameTypeStr = rs.getString("game_type");
                if (gameTypeStr.equals(GameInfoEnum.DIG_GOLD.name())) {
                    hgi.setGameType(GameInfoEnum.DIG_GOLD);
                } else if (gameTypeStr.equals(GameInfoEnum.HIDE_AND_SEEK.name())) {
                    hgi.setGameType(GameInfoEnum.HIDE_AND_SEEK);
                }
                hgi.setGameCenterAddress(rs.getString("game_center_address"));
                hgi.setGameCenterLatitude(rs.getString("game_center_latitude"));
                hgi.setGameCenterLongitude(rs.getString("game_center_longitude"));
                hgi.setGameCenterRadius(rs.getString("game_center_radius"));
                hgi.setGameEndDate(rs.getDate("game_end_date"));
                hgi.setGameEndTime(rs.getTimestamp("game_end_time"));
                hgi.setGameGrabLogoDistance(rs.getLong("game_grab_logo_distance"));
                hgi.setGameHideTime(rs.getLong("game_hide_time"));
                hgi.setGameHideTimeInvalid(rs.getLong("game_hide_time_invalid"));
                hgi.setGameLogoDisplayRadius(rs.getString("game_logo_display_radius"));
                hgi.setGameLogoName(rs.getString("game_logo_name"));
                hgi.setGameLogoPutInPointLatitude(rs.getString("game_logo_put_in_point_latitude"));
                hgi.setGameLogoPutInPointLongitude(rs.getString("game_logo_put_in_point_longitude"));
                hgi.setGameLogoUri(rs.getString("game_logo_uri"));
                hgi.setGameMaxGetSpeed(rs.getLong("game_max_get_speed"));
                hgi.setGameMaxMoveSpeed(rs.getLong("game_max_move_speed"));
                hgi.setGameRobGoldTimes(rs.getInt("game_rob_gold_times"));
                hgi.setGameShareTextTemplate(rs.getString("game_share_text_template"));
                hgi.setGameStartDate(rs.getDate("game_start_date"));
                hgi.setGameStartTime(rs.getTimestamp("game_start_time"));
                hgi.setGameLogoPutInPointAddress(rs.getString("game_logo_put_in_point_address"));
                hgi.setCreatedTime(rs.getTimestamp("created_time"));
                hgi.setLastModTime(rs.getTimestamp("last_mod_time"));
                hgi.setOperator(rs.getString("operator"));

                hgiList.add(hgi);
            }
            return hgiList;
        }
    }
}
