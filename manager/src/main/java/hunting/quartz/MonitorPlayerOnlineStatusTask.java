package hunting.quartz;

import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.PlayersDao;
import hunting.common.init.PlayerHoldLogoInit;
import hunting.common.pojo.HuntingGamePlayerHoldLogo;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojoenum.PlayerHoldLogoEnum;
import hunting.common.utils.DateUtil;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 监控玩家状态的定时任务
 * @author yunan.zheng
 *
 */
@Service("playerOnlineStatusTask")
public class MonitorPlayerOnlineStatusTask {
    
    @Resource
    private PlayersDao playersDao;
    
    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;
    
    private long intervalTime = 1000*60;
    
    public void execute(){
        long currentTime = System.currentTimeMillis();
        List<HuntingGamePlayers> gpList = playersDao.listLTLastModTime(DateUtil.format(new Date(currentTime-intervalTime), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        if (!CollectionUtils.isEmpty(gpList)) {
            for (HuntingGamePlayers gp : gpList) {
                gp.setOnline(false);
                gp.setLastModTime(new Date(currentTime));
                
                try {
                    HuntingGamePlayerHoldLogo playerHoldLogoDB = playerHoldLogoDao.get(gp.getGameInfoId());
                    if(playerHoldLogoDB!=null&&gp.getPlayerId().equalsIgnoreCase(playerHoldLogoDB.getPlayerId())){
                        playerHoldLogoDB.setHoldType(PlayerHoldLogoEnum.UNHOLD);
                        playerHoldLogoDao.update(playerHoldLogoDB);
                    }                   
                    HuntingGamePlayerHoldLogo playerHoldLogo =  PlayerHoldLogoInit.getPlayerHoldLogo(gp.getGameCity());
                    if(playerHoldLogo!=null&&gp.getPlayerId().equalsIgnoreCase(playerHoldLogo.getPlayerId())){
                        PlayerHoldLogoInit.removePlayerHoldLogo(gp.getGameCity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            playersDao.batchUpdate(gpList);
        }
    }
}
