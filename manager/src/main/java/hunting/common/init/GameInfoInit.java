package hunting.common.init;

import hunting.common.dao.GameInfoDao;
import hunting.common.pojo.HuntingGameInfo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


/**
 * 当前游戏数据初始化，放在内存中
 * @author yunan.zheng
 *
 */
@Service
public class GameInfoInit {
    
    private final Logger logger = LoggerFactory.getLogger(GameInfoInit.class);
    
    private static volatile ConcurrentHashMap<String,HuntingGameInfo> gameInfos = new ConcurrentHashMap<String,HuntingGameInfo>();

    @Resource
    private GameInfoDao gameInfoDao;

    /**
     * 任务完成后，10秒再执行一次
     */
    public void cache() {
        logger.info(" time is {},GameInfoInit start ...",System.currentTimeMillis());
        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        executors.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    List<HuntingGameInfo> list = gameInfoDao.getCurrentGameInfoList();
                    if (CollectionUtils.isEmpty(list)) {
                        logger.info(" GameInfoInit list is null ");
                        return;
                    }
                    logger.info(" GameInfoInit list.size is {} ",list.size());
                    for (HuntingGameInfo gi : list) {
                        HuntingGameInfo hgi = gameInfos.get(gi.getGameCity());
                        if (hgi!=null) {
                            //TODO需要修改last_mod_time
                           if( hgi.getLastModTime().getTime() == gi.getLastModTime().getTime()){
                               logger.info(" GameInfoInit gameCity is {},gameInfoId is {}  content is same. ",gi.getGameCity(),gi.getId());
                               continue;
                           }
                        }
                        gameInfos.put(gi.getGameCity(), gi);
                        logger.info(" GameInfoInit gameCity is {},gameInfoId is {} ",gi.getGameCity(),gi.getId());
                    }
                    long endTime = System.currentTimeMillis();
                    logger.info(" GameInfoInit run cost   {} ",endTime-startTime);
                } catch (Exception e) {
                    logger.error(" GameInfoInit run error {} ",e);
                }
            }
        }, 3, 10, TimeUnit.SECONDS);

    }

    public static ConcurrentHashMap<String,HuntingGameInfo> getGameInfos() {
        return gameInfos;
    }
    
    public static HuntingGameInfo getGameInfo(String gameCity) {
        return gameInfos.get(gameCity);
    }
    
    
    /**
     * 清理过期的数据
     */
    public static void removeGameInfos(String city){
      gameInfos.remove(city);
    }
}
