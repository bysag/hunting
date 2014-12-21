package hunting.common.init;

import hunting.common.dao.PlayersDao;
import hunting.common.maptree.HuntingDtree;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.utils.DateUtil;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * 玩家位置初始化，放在内存中
 * 
 * @author yunan.zheng
 * 
 */
@Service
public class PlayerMoveRecordInit {

    private final Logger logger = LoggerFactory.getLogger(PlayerMoveRecordInit.class);

    private static volatile ConcurrentHashMap<String, ConcurrentHashMap<String, HuntingGamePlayers>> moveRecords = new ConcurrentHashMap<String, ConcurrentHashMap<String, HuntingGamePlayers>>();

    private static volatile ConcurrentHashMap<String,HuntingDtree> huntingdtree  = new ConcurrentHashMap<String,HuntingDtree>();;
    
    private Map<Long, Long> lastModTimeMap = new HashMap<Long, Long>();

    @Resource
    private PlayersDao playersDao;
    
    private long lastModTime = DateUtil.parse("2014-01-01 00:00:00",
            DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS).getTime();


    /**
     * 任务完成后，10秒再执行一次
     */
    public void cache() {
        logger.info(" time is {},PlayerMoveRecordInit start ...", System.currentTimeMillis());
        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        executors.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    Map<String,Boolean> rebuildDtreeMap = new HashMap<String, Boolean>();
                    ConcurrentHashMap<String, HuntingGameInfo> gameinfos = GameInfoInit
                            .getGameInfos();
                    if (CollectionUtils.isEmpty(gameinfos)) {
                        return;
                    }
                    Set<String> gameCitys = gameinfos.keySet();
                    for (String city : gameCitys) {
                        long gameInfoId = gameinfos.get(city).getId();
                        long currentLastModTime = lastModTime ; 
                        if(lastModTimeMap.get(gameInfoId)!=null){
                            currentLastModTime = lastModTimeMap.get(gameInfoId);
                        }
                        List<HuntingGamePlayers> records = playersDao.incrementList(gameInfoId,DateUtil.format(new Date(currentLastModTime),DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS),5000);//(gameInfoId);
                        if (CollectionUtils.isEmpty(records)) {
                            logger.info(" PlayerMoveRecordInit gameCity is {} list is null ", city);
                            continue;
                        }
                        currentLastModTime = records.get(records.size()-1).getLastModTime().getTime();
                        lastModTimeMap.put(gameInfoId,currentLastModTime);  
                        rebuildDtreeMap.put(city, true);
                        logger.info(" PlayerMoveRecordInit gameCity is{}, list.size is {} ", city,
                                records.size());
                        ConcurrentHashMap<String, HuntingGamePlayers> subRecords = moveRecords.get(city);
                        if(subRecords==null){
                            subRecords = new ConcurrentHashMap<String, HuntingGamePlayers>();
                        }
                        for (HuntingGamePlayers pmr : records) {
                            if (pmr.isOnline()==true) {
                                subRecords.put(pmr.getPlayerId(), pmr); 
                            }else {
                                subRecords.remove(pmr.getPlayerId());   
                            }
                        }
                        moveRecords.put(city, subRecords);
                    }

                    /**
                     * 坐标Dtree结构
                     */
                  
                    Set<String> gameCityySet = moveRecords.keySet();
                    for (String gameCity : gameCityySet) {
                        if(rebuildDtreeMap.get(gameCity)){
                            ConcurrentHashMap<String, HuntingGamePlayers> pmrMap = moveRecords
                                    .get(gameCity);
                            Collection<HuntingGamePlayers> pmrs = pmrMap.values();
                            HuntingDtree newTree = new HuntingDtree();
                            for (HuntingGamePlayers pmr : pmrs) {
                                newTree.insert(
                                        Double.parseDouble(pmr.getLatitude()),
                                        Double.parseDouble(pmr.getLongitude()),
                                        new HuntingDtree.NodeData(pmr.getPlayerId(), pmr
                                                .getGameCity(), pmr.getGameInfoId()));
                            }
                            
                            huntingdtree.put(gameCity,newTree);
                        }
                    }
                   
                    long endTime = System.currentTimeMillis();
                    logger.info(" PlayerMoveRecordInit run cost   {} ", endTime - startTime);
                } catch (Exception e) {}
            }
        }, 1, 10, TimeUnit.SECONDS);

    }

    public static ConcurrentHashMap<String, HuntingGamePlayers> getGameCityMoveRecord(
            String gameCity) {
        return moveRecords.get(gameCity);
    }

    public static HuntingGamePlayers getPlayerInfo(String gameCity, String playerId) {
        return moveRecords.get(gameCity).get(playerId);
    }

    public static HuntingDtree getHuntingTree(String gameCity) {
        return huntingdtree.get(gameCity);
    }

    /**
     * 清理过期的moveRecord
     */
    public static void remvoeMoveRecord(String city) {
            moveRecords.remove(city);
            huntingdtree.remove(city);
    }
}
