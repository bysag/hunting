/**
 * 
 */
package hunting.manager.service;

import hunting.common.dao.PlayersDao;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojo.OOPlayerInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 玩家服务类
 * @author yunan.zheng
 *
 */
@Service
public class GamePlayerService {
    
    @Resource
    private PlayersDao playersDao;
    
    
    public long getOnlineAmount(String gameCity){
        return playersDao.getOnlineAmount(gameCity);
    }
    
    public List<HuntingGamePlayers> getAllPlayers(long gameInfoId){
        return playersDao.list(gameInfoId);
    }
    
    public OOPlayerInfo getPlayerByPlayerId(String playerId,long gameInfoId){
        return playersDao.get(playerId,gameInfoId);
    }
}
