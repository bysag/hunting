package hunting.common.dao;

import hunting.common.pojo.HuntingGameGlobalConfig;
import hunting.common.pojoenum.GlobalConfigEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GlobalConfigDao {


    @Resource
    private JdbcTemplate jdbcTemplate;

    private String INSERT = "INSERT INTO hunting_game_global_config (config_key,config_value, created_time, config_type)VALUES(?,?,?,?);";

    private String SELECT_COMMOM = "SELECT  id, config_key, config_value, created_time, config_type FROM hunting_game_global_config ";

    private String DELETE = " DELETE FROM hunting_game_global_config WHERE config_key = ? and config_type= ? ;";
    
    public int insert(HuntingGameGlobalConfig hggc) {
        int result = jdbcTemplate.update(INSERT,
                new Object[] { hggc.getConfigKey(), hggc.getConfigValue(), hggc.getCreatedTime(),
                hggc.getConfigType().name() });
        return result;
    }

    public int delete(String gameCity,GlobalConfigEnum configType) {
        int result = jdbcTemplate.update(DELETE,
                new Object[] {gameCity ,configType.name() });
        return result;
    }

    
    public List<HuntingGameGlobalConfig> list(String gameCity, GlobalConfigEnum globalConfigEnum) {
        StringBuilder sbr = new StringBuilder(SELECT_COMMOM);
        sbr.append("where 1=1  ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.hasLength(gameCity)) {
            sbr.append(" and config_key like '%"+gameCity+"%' ");
        }

        if (globalConfigEnum != null) {
            sbr.append(" and config_type= ?");
            params.add(globalConfigEnum.name());
        }
        return jdbcTemplate.query(sbr.toString(), params.toArray(),
                new ResultSetExtractor<List<HuntingGameGlobalConfig>>() {

                    @Override
                    public List<HuntingGameGlobalConfig> extractData(ResultSet rs)
                            throws SQLException, DataAccessException {
                        List<HuntingGameGlobalConfig> res = new ArrayList<HuntingGameGlobalConfig>();
                        while (rs.next()) {
                            HuntingGameGlobalConfig hggc = new HuntingGameGlobalConfig();
                            hggc.setId(rs.getLong("id"));
                            hggc.setConfigKey(rs.getString("config_key"));
                            hggc.setConfigValue(rs.getString("config_value"));
                            hggc.setCreatedTime(rs.getDate("created_time"));
                            String configType = rs.getString("config_type");
                            if (configType.equals(GlobalConfigEnum.GAME_CITY.name())) {
                                hggc.setConfigType(GlobalConfigEnum.GAME_CITY);
                            }else if(configType.equals(GlobalConfigEnum.GAME_RULE.name())){
                                hggc.setConfigType(GlobalConfigEnum.GAME_RULE);
                            }
                            res.add(hggc);
                        }
                        return res;
                    }

                });
    }
}
