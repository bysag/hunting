package hunting.common.init;

import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGamePlayerHoldLogo;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class PlayerHoldLogoInit {

    private final Logger logger = LoggerFactory.getLogger(PlayerHoldLogoInit.class);
    
    private static volatile ConcurrentHashMap<String,HuntingGamePlayerHoldLogo> holdLogo = new ConcurrentHashMap<String,HuntingGamePlayerHoldLogo>();

    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;

    /**
     * 任务完成后，2秒再执行一次
     */
    public void cache() {
        
        logger.info(" time is {},PlayerHoldLogoInit start ...",System.currentTimeMillis());
        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        executors.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    ConcurrentHashMap<String, HuntingGameInfo> gameinfos = GameInfoInit
                            .getGameInfos();
                    if (CollectionUtils.isEmpty(gameinfos)) {
                        return;
                    }
                    Set<String> gameCitys = gameinfos.keySet();
                    for (String city : gameCitys) {
                        long gameInfoId = gameinfos.get(city).getId();
                        HuntingGamePlayerHoldLogo phl = playerHoldLogoDao.get(gameInfoId);
                        if (phl==null) {
                            logger.info(" PlayerHoldLogoInit is null,city is {} ",city);
                            holdLogo.remove(city);
                            continue;
                        }
                        holdLogo.put(city, phl);
                        logger.info(" PlayerHoldLogoInit holder is {} ,city is {}",phl.getPlayerId(),city);
                    }
                    long endTime = System.currentTimeMillis();
                    logger.info(" PlayerHoldLogoInit run cost   {} ",endTime-startTime);
                } catch (Exception e) {
                    logger.error(" PlayerHoldLogoInit run error {} ",e);
                }
            }
        }, 3, 2, TimeUnit.SECONDS);

    }

    public static ConcurrentHashMap<String,HuntingGamePlayerHoldLogo> getholdLogo() {
        return holdLogo;
    }
    
    public static HuntingGamePlayerHoldLogo getPlayerHoldLogo(String gameCity) {
        return holdLogo.get(gameCity);
    }
    

    /**
     * 清理过期
     */
    public static void removePlayerHoldLogo(String city) {
        holdLogo.remove(city);
    }
}
