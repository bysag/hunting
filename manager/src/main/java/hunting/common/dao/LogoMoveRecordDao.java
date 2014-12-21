/**
 * 
 */
package hunting.common.dao;

import java.util.List;

import hunting.common.pojo.HuntingGameLogoMoveRecord;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


/**
 * @author yunan.zheng
 *
 */
@Service
public class LogoMoveRecordDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    
    private String SELECT_COMMON = "SELECT  id, game_info_id, logo_longtitude, logo_latitude, created_time FROM hunting_game_logo_move_record ";
    
    private String INSERT = "INSERT INTO hunting_game_logo_move_record (game_info_id, logo_longtitude, logo_latitude, created_time, game_city)VALUES(?,?,?,?,?);";
    
    public HuntingGameLogoMoveRecord get(long gameInfoId){
        StringBuilder sbr = new StringBuilder(SELECT_COMMON);
        sbr.append(" where  game_info_id = ? ");
        sbr.append(" order by created_time desc limit 1");
        List<HuntingGameLogoMoveRecord> res = jdbcTemplate.query(sbr.toString(), new Object[]{gameInfoId},new BeanPropertyRowMapper<HuntingGameLogoMoveRecord>(HuntingGameLogoMoveRecord.class));
        if (CollectionUtils.isEmpty(res)) {
            return null;
        }
        return res.get(0);
    }
    
    public int insert(HuntingGameLogoMoveRecord lmr){
      return jdbcTemplate.update(INSERT, new Object[]{lmr.getGameInfoId(),lmr.getLogoLongtitude(),lmr.getLogoLatitude(),lmr.getCreatedTime(),lmr.getGameCity()}); 
    }
}
