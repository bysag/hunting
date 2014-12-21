package hunting.common.init;

import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGameLogoMoveRecord;
import hunting.manager.service.LogoMoveRecordService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 车标位置初始化，放在内存中
 * 
 * @author yunan.zheng
 * 
 */
@Service
public class LogoMoveRecordInit {

    private final Logger logger = LoggerFactory.getLogger(LogoMoveRecordInit.class);

    private static volatile ConcurrentHashMap<String,HuntingGameLogoMoveRecord> logoMoveRecords = new ConcurrentHashMap<String,HuntingGameLogoMoveRecord>();

    @Resource
    private LogoMoveRecordService logoMoveRecordService;


    /**
     * 任务完成后，10秒再执行一次
     */
    public void cache() {
        logger.info(" time is {},LogoMoveRecordInit start ...",System.currentTimeMillis());

        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        executors.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    ConcurrentHashMap<String,HuntingGameInfo> gameinfos = GameInfoInit.getGameInfos();
                    Set<String> gameCitySet = gameinfos.keySet();
                    for (String city : gameCitySet) {
                        HuntingGameLogoMoveRecord lmr = logoMoveRecordService.getLastestLogoLngLat(gameinfos.get(city).getId());
                        if(lmr==null){
                            logger.info(" LogoMoveRecordInit HuntingGameLogoMoveRecord  is null, gameCity is {} ",city);
                            logoMoveRecords.remove(city);
                        }else{
                            logger.info(" LogoMoveRecordInit HuntingGameLogoMoveRecord  is null, gameCity is {} ,logoMoveRecord'id is {}",city,lmr.getId());
                            logoMoveRecords.put(city,lmr);
                        }
                        long endTime = System.currentTimeMillis();
                        logger.info(" LogoMoveRecordInit run cost   {} ",endTime-startTime);
                    }
                } catch (Exception e) {
                    logger.info(" LogoMoveRecordInit error  {} ",e);
                }
            }
        }, 1, 5, TimeUnit.SECONDS);

    }

    public static HuntingGameLogoMoveRecord getGameCityLogoMoveRecord(
            String gameCity) {
        return logoMoveRecords.get(gameCity);
    }

    /**
     * 清理过期的logoMoveRecord
     */
    public static void removeLogoMoveRecord(String city) {
        logoMoveRecords.remove(city);
    }
}
