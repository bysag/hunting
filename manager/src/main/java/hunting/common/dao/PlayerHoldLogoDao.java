/**
 * 
 */
package hunting.common.dao;

import hunting.common.pojo.HuntingGamePlayerHoldLogo;
import hunting.common.pojoenum.PlayerHoldLogoEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


/**
 * 车标持有类
 * @author yunan.zheng
 *
 */
@Service
public class PlayerHoldLogoDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    private String SELECT_COMON = "SELECT id,player_id, hold_logo_start_time, hold_logo_end_time, game_info_id, hold_type,game_city  FROM hunting_game_player_hold_logo ";
    
    private String INSERT = "INSERT INTO hunting_game_player_hold_logo (player_id, hold_logo_start_time,hold_logo_end_time,game_info_id,hold_type,game_city) VALUES(?,?,?, ?, ?,?);";
    
    private String UPDATE = "UPDATE hunting_game_player_hold_logo SET hold_logo_end_time = now() ,hold_type = ? WHERE id = ? ";
    
    private String TOP_SELECT = " select count(*) as amount, player_id as playerId from hunting_game_player_hold_logo  ";
   
    private String TOP_SELECT_AMOUNT = " select COUNT(DISTINCT player_id) AS amount from hunting_game_player_hold_logo ";
    
    private String SORT_SELECT = " SELECT DISTINCT amount FROM ( SELECT COUNT(*) AS amount ,player_id FROM hunting_game_player_hold_logo where player_id NOT IN ( SELECT player_id FROM hunting_game_blacklist WHERE TYPE='VALID') and game_city=? and hold_logo_start_time >=? and hold_logo_start_time <=? GROUP BY player_id ) a order by amount desc ";
    
    private String CITY_GROUP = " select game_city from hunting_game_player_hold_logo ";
    
    public List<String> getCity(String date){
        StringBuilder sbr = new StringBuilder(CITY_GROUP);
        sbr.append(" where hold_logo_start_time >= ? and hold_logo_start_time<= ? group by game_city ;");
        List<Object> params = new ArrayList<Object>();
        params.add(date+" 00:00:00");
        params.add(date+" 23:59:59");
        return jdbcTemplate.queryForList(sbr.toString(), params.toArray(),String.class);
    }
    
    public int insert(HuntingGamePlayerHoldLogo phl){
        return jdbcTemplate.update(INSERT,new Object[]{phl.getPlayerId(),phl.getHoldLogoStartTime(),phl.getHoldLogoEndTime(),phl.getGameInfoId(),phl.getHoldType().name(),phl.getGameCity()});
    }
    
    public int update(HuntingGamePlayerHoldLogo phl){
        return jdbcTemplate.update(UPDATE,new Object[]{phl.getHoldType().name(),phl.getId()});
    }
    
    public List<Map<String, Object>> getAmountByPlayerIds(List<String> playerIds){
        StringBuilder sbr = new StringBuilder(TOP_SELECT);
        sbr.append(" where player_id in (  ");
        if(!playerIds.isEmpty()){
            for (String playerId : playerIds) {
               sbr.append("'");
               sbr.append(playerId);
               sbr.append("',");                
            }
            sbr.append("'1') ");
        }
        sbr.append(" group by player_id  ");
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sbr.toString());
        return res;
    }
    
    
    
    public List<Integer> sort(String gameCity,String startDate,String endDate){
        StringBuilder sbr = new StringBuilder(SORT_SELECT);
        List<Integer> res = jdbcTemplate.queryForList(sbr.toString(),new Object[]{gameCity,startDate,endDate},Integer.class);
        return res;
    }
    
    public List<Map<String, Object>> top(String gameCity,String startDate,String endDate, int limit ,int offset){
        StringBuilder sbr = new StringBuilder(TOP_SELECT);
        List<Object> params  = new ArrayList<Object>();
        sbr.append(" where player_id NOT IN ( SELECT player_id FROM hunting_game_blacklist WHERE TYPE='VALID') AND game_city = ? ");
        params.add(gameCity);
        if (StringUtils.hasLength(startDate)) {
            sbr.append(" and hold_logo_start_time  >= ? ");
            params.add(startDate);
        }
        if (StringUtils.hasLength(endDate)) {
            sbr.append(" and hold_logo_start_time  <= ? ");
            params.add(endDate);
        }        
        sbr.append(" group by player_id order by amount desc  limit ? offset ? ");
        params.add(limit);
        params.add(offset);
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sbr.toString(),params.toArray());
        return res;
    }
    
    public int topAmount(String gameCity,String startDate,String endDate){
        StringBuilder sbr = new StringBuilder(TOP_SELECT_AMOUNT);
        List<Object> params  = new ArrayList<Object>();
        sbr.append(" where player_id NOT IN ( SELECT player_id FROM hunting_game_blacklist WHERE TYPE='VALID') AND game_city = ? ");
        params.add(gameCity);
        if (StringUtils.hasLength(startDate)) {
            sbr.append(" and hold_logo_start_time  >= ? ");
            params.add(startDate);
        }
        if (StringUtils.hasLength(endDate)) {
            sbr.append(" and hold_logo_start_time  <= ? ");
            params.add(endDate);
        }        
        int result =  0;
        try {
            result = jdbcTemplate.queryForInt(sbr.toString(),params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
    
    public HuntingGamePlayerHoldLogo get(long gameInfoId){
        HuntingGamePlayerHoldLogo hgphl = null;
        StringBuilder sbr = new StringBuilder(SELECT_COMON);
        sbr.append(" where hold_type= ? and game_info_id = ? ");
        List<Object> params = new ArrayList<Object>();
        params.add(PlayerHoldLogoEnum.HOLD.name());
        params.add(gameInfoId);
        List<HuntingGamePlayerHoldLogo> resList = jdbcTemplate.query(sbr.toString(), params.toArray(),new PlayerHoldLogoResult());
        if(!CollectionUtils.isEmpty(resList)){
            hgphl = resList.get(0);
        }
        return hgphl;
    }
    
    
    class PlayerHoldLogoResult implements ResultSetExtractor<List<HuntingGamePlayerHoldLogo>>{

        @Override
        public List<HuntingGamePlayerHoldLogo> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            List<HuntingGamePlayerHoldLogo> res = new ArrayList<HuntingGamePlayerHoldLogo>();
            while (rs.next()) {
                HuntingGamePlayerHoldLogo phl = new HuntingGamePlayerHoldLogo();
                phl.setId(rs.getLong("id"));
                phl.setPlayerId(rs.getString("player_id"));
                phl.setHoldLogoStartTime(rs.getTimestamp("hold_logo_start_time"));
                phl.setHoldLogoEndTime(rs.getTimestamp("hold_logo_end_time"));
                phl.setGameInfoId(rs.getLong("game_info_id"));
                phl.setGameCity(rs.getString("game_city"));
                String holdType = rs.getString("hold_type");
                if(holdType.equals(PlayerHoldLogoEnum.HOLD.name())){
                    phl.setHoldType(PlayerHoldLogoEnum.HOLD);
                }else {
                    phl.setHoldType(PlayerHoldLogoEnum.UNHOLD);
                }
                res.add(phl);
            }
            return res;
        }
        
    }
}
