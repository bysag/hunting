/**
 * 
 */
package hunting.manager.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import hunting.common.dao.GlobalConfigDao;
import hunting.common.pojo.HuntingGameGlobalConfig;
import hunting.common.pojoenum.GlobalConfigEnum;


/**
 * @author yunan.zheng
 *
 */
@Service
public class GlobalConfigService {
    
    @Resource
    private GlobalConfigDao globalConfigDao;
    
    /**
     * 添加城市
     * @param gameCity
     * @return  0 添加失败 ； 1 添加成功 ；2城市已存在
     */
    public int saveGameCity(String gameCity){
        int result = 0 ;
        List<HuntingGameGlobalConfig> res = getGameCity(gameCity);
        int size = res.size();
        if (size==0) {
            HuntingGameGlobalConfig hggc = new HuntingGameGlobalConfig();
            hggc.setCreatedTime(new Date());
            hggc.setConfigKey(gameCity);
            hggc.setConfigValue(gameCity);
            hggc.setConfigType(GlobalConfigEnum.GAME_CITY);
            result = globalConfigDao.insert(hggc); 
        }else if(size>0){
            result = 2 ;
        }
        return result;
    }
    
    /**
     * 添加游戏规则
     * @param gameCity
     * @param gameRule
     * @return  0 添加失败 ； 1 添加成功 ；
     */
    public int saveGameRule(String gameCity,String gameRule){
        int result = 0 ;
        List<HuntingGameGlobalConfig> res = getGameCity(gameCity);
        int size = res.size();
        if(size>0){
            globalConfigDao.delete(gameCity,GlobalConfigEnum.GAME_RULE);
        }
        HuntingGameGlobalConfig hggc = new HuntingGameGlobalConfig();
        hggc.setCreatedTime(new Date());
        hggc.setConfigKey(gameCity);
        hggc.setConfigValue(gameRule);
        hggc.setConfigType(GlobalConfigEnum.GAME_RULE);
        result = globalConfigDao.insert(hggc); 
        return result;
    }
    
    public List<HuntingGameGlobalConfig> getGameCity(){
        return getGameCity(null);
    }
    
    public List<HuntingGameGlobalConfig> getGameCity(String gameCity){
        return globalConfigDao.list(gameCity, GlobalConfigEnum.GAME_CITY);
    }
    
    public HuntingGameGlobalConfig getGameRule(String gameCity){
        List<HuntingGameGlobalConfig> result = globalConfigDao.list(gameCity, GlobalConfigEnum.GAME_RULE);
        if(CollectionUtils.isEmpty(result)) return null;
        return result.get(0);
    } 
}
