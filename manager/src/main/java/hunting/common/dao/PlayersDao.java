package hunting.common.dao;

import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojo.OOPlayerInfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlayersDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    
    private String SELECT_COMMON = "SELECT  id, player_id, game_info_id, created_time, last_mod_time, online, longitude, latitude,game_city FROM hunting_game_players ";
    
    private String UPDATE = "UPDATE hunting_game_players SET last_mod_time = ? ,online = ? , longitude = ? ,latitude = ?  WHERE  player_id = ? and game_info_id = ? ;";
    
    private String INSERT = "INSERT INTO hunting_game_players (player_id, game_info_id, created_time, last_mod_time, online, longitude, latitude,game_city) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
    
    public long getOnlineAmount(String gameCity){
        return jdbcTemplate.queryForInt("select count(*) from hunting_game_players where online=1 and game_city = ?" ,new Object[]{gameCity});
    }
    
    public List<HuntingGamePlayers> list(long gameInfoId){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_info_id = ? ");
        sbr.append(" and online = true ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameInfoId},new BeanPropertyRowMapper<HuntingGamePlayers>(HuntingGamePlayers.class));
    }
    
    public List<HuntingGamePlayers> incrementList(long gameInfoId,String lastModTime,int limit){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_info_id = ? ");
        sbr.append(" and last_mod_time > ? order by id desc limit ? ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameInfoId,lastModTime,limit},new BeanPropertyRowMapper<HuntingGamePlayers>(HuntingGamePlayers.class));
    }
    
    public List<HuntingGamePlayers> listByLastModTime(String lastModTime){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where last_mod_time >= ? and online = 1 ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{lastModTime},new BeanPropertyRowMapper<HuntingGamePlayers>(HuntingGamePlayers.class));
    }
    
    public List<HuntingGamePlayers> listLTLastModTime(String lastModTime){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where last_mod_time <= ? and online = 1 ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{lastModTime},new BeanPropertyRowMapper<HuntingGamePlayers>(HuntingGamePlayers.class));
    }
    
    public List<HuntingGamePlayers> list(String gameCity){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_city = ? ");
        sbr.append(" and online = 1 ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameCity},new BeanPropertyRowMapper<HuntingGamePlayers>(HuntingGamePlayers.class));
    }
    
    public int insert(HuntingGamePlayers gp){
       return jdbcTemplate.update(INSERT,new Object[]{gp.getPlayerId(),gp.getGameInfoId(),gp.getCreatedTime(),gp.getLastModTime(),gp.isOnline(),gp.getLongitude(),gp.getLatitude(),gp.getGameCity()});
    }
  
    public int update(HuntingGamePlayers gp){
        return jdbcTemplate.update(UPDATE,new Object[]{gp.getLastModTime(),gp.isOnline(),gp.getLongitude(),gp.getLatitude(),gp.getPlayerId(),gp.getGameInfoId()});
     }
    
    public int[] batchUpdate(List<HuntingGamePlayers> gpList){
        List<Object[]> updateList = new ArrayList<Object[]>();
        for (HuntingGamePlayers gp : gpList) {
            List<Object> params = new ArrayList<Object>();
            params.add(gp.getLastModTime());
            params.add(gp.isOnline());
            params.add(gp.getLongitude());
            params.add(gp.getLatitude());
            params.add(gp.getPlayerId());
            params.add(gp.getGameInfoId());
            updateList.add(params.toArray());
        }
        return jdbcTemplate.batchUpdate(UPDATE, updateList); 
     }
    
    public OOPlayerInfo get(String playerId,long gameInfoId){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where player_id = ? and game_info_id = ? ");
        List<OOPlayerInfo> res = jdbcTemplate.query(sbr.toString(), new Object[]{playerId,gameInfoId},new BeanPropertyRowMapper<OOPlayerInfo>(OOPlayerInfo.class));
        if(res.isEmpty()){
            return null;
        }
        return res.get(0);
        
    }
}
