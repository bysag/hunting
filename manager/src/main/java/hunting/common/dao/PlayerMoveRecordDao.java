/**
 * 
 */
package hunting.common.dao;

import hunting.common.pojo.HuntingGamePlayerMoveRecord;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * @author yunan.zheng
 *
 */
@Service
public class PlayerMoveRecordDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    
    private String INSERT = "INSERT INTO hunting_game_player_move_record (player_id, move_longtitude, move_latitude, created, game_info_id, game_city)  VALUES(?, ?, ?, ?, ?,?);";
    private String SELECT_COMMON = "SELECT  distinct player_id, move_longtitude, move_latitude, created, game_info_id FROM hunting_game_player_move_record ";
    
    
    public int insert(List<HuntingGamePlayerMoveRecord>  pmrs){
        if(pmrs.isEmpty()) return 0;
        List<Object[]> params = new ArrayList<Object[]>();
        for (HuntingGamePlayerMoveRecord gmr : pmrs) {
            List<Object> elements = new ArrayList<Object>();
            elements.add(gmr.getPlayerId());
            elements.add(gmr.getMoveLongtitude());
            elements.add(gmr.getMoveLatitude());
            elements.add(gmr.getCreated());
            elements.add(gmr.getGameInfoId());
            elements.add(gmr.getGameCity());
            params.add(elements.toArray());
        }
        int [] result = jdbcTemplate.batchUpdate(INSERT,params);
        return result.length;
    }
    
    public List<HuntingGamePlayerMoveRecord> list(long gameInfoId){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where game_info_id = ? ");
        sbr.append(" order by created desc ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{gameInfoId},new BeanPropertyRowMapper<HuntingGamePlayerMoveRecord>(HuntingGamePlayerMoveRecord.class));
    }

    public List<HuntingGamePlayerMoveRecord> list(long id,int limit){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where id > ? ");
        sbr.append(" limit ? offset ?  ");
        return jdbcTemplate.query(sbr.toString(), new Object[]{id,limit},new BeanPropertyRowMapper<HuntingGamePlayerMoveRecord>(HuntingGamePlayerMoveRecord.class));
    }
}
