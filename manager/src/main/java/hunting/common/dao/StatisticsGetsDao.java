package hunting.common.dao;

import hunting.common.pojo.HuntingGameStatisticsGets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatisticsGetsDao {
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    
    private String INSERT = "INSERT INTO hunting_game_statistics_gets ( game_city, player_id, gets, sort, created_time, sort_date,type)   VALUES ( ?, ?, ?, ?, ?, ?,?);";
    
    private String SELECT_COMMON = "SELECT  id, game_city, player_id, gets, sort, created_time, sort_date, TYPE FROM hunting_game_statistics_gets ";
    
    
    public int insert(List<HuntingGameStatisticsGets> sgs)
    {
        if (sgs.isEmpty()) {
            return 0;
        }
        List<Object[]>batchArgs = new ArrayList<Object[]>();
        for (HuntingGameStatisticsGets sg : sgs) {
          List<Object> elements = new ArrayList<Object>();
          elements.add(sg.getGameCity());
          elements.add(sg.getPlayerId());
          elements.add(sg.getGets());
          elements.add(sg.getSort());
          elements.add(sg.getCreatedTime());
          elements.add(sg.getSortDate());
          elements.add(sg.getType());
          batchArgs.add(elements.toArray());
        }
        int[] res = jdbcTemplate.batchUpdate(INSERT, batchArgs);
        return res.length;
    }
    
    public List<HuntingGameStatisticsGets> list(String gameCity,String playerId,String type){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_city = ? and player_id = ? and type= ? order by sort desc limit 1;");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameCity,playerId,type},new BeanPropertyRowMapper<HuntingGameStatisticsGets>(HuntingGameStatisticsGets.class));
    }
    
    public List<HuntingGameStatisticsGets> list(String gameCity,String playerId,String type,String day){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_city = ? and player_id = ? and type= ? and sort_date = ?  limit 1;");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameCity,playerId,type,day},new BeanPropertyRowMapper<HuntingGameStatisticsGets>(HuntingGameStatisticsGets.class));
    }
}
