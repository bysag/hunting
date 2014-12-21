package hunting.common.init;

import hunting.common.dao.BlackListDao;
import hunting.common.pojo.HuntingGameBlackList;
import hunting.common.utils.DateUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 黑名单初始化,把数据放在内存中
 * 
 * @author yunan.zheng
 * 
 */
@Service
public class BlackListInit {

    private final Logger logger = LoggerFactory.getLogger(BlackListInit.class);

    private static volatile CopyOnWriteArraySet<String> blackListSet = new CopyOnWriteArraySet<String>();

    @Resource
    private BlackListDao blackListDao;

    private int limit = 1000;

    private long lastModTime = DateUtil.parse("2014-01-01 00:00:00",
            DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS).getTime();

    /**
     * 任务完成后，10秒再执行一次
     */
    public void cache() {
        logger.info(" time is {},BlackListInit start ...", System.currentTimeMillis());
        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        executors.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();

                    List<HuntingGameBlackList> list = blackListDao
                            .incrementList(lastModTime, limit);
                    if (CollectionUtils.isEmpty(list)) {
                        logger.info(" BlackListInit list is null ");
                        return;
                    }
                    lastModTime = list.get(list.size() - 1).getLastModTime().getTime();
                    logger.info(" BlackListInit list.size is {} ", list.size());
                    for (HuntingGameBlackList bl : list) {
                        switch (bl.getType()) {
                            case VALID:
                                blackListSet.add(bl.getPlayerId());
                                break;
                            case INVALID:
                                blackListSet.remove(bl.getPlayerId());
                                break;
                            default:
                                break;
                        }
                    }

                    long endTime = System.currentTimeMillis();
                    logger.info(" BlackListInit run cost   {} ", endTime - startTime);
                } catch (Exception e) {
                    logger.error(" BlackListInit run error {} ", e);
                }

            }
        }, 3, 10, TimeUnit.SECONDS);

    }

    public static CopyOnWriteArraySet<String> getBlackListSet() {
        return blackListSet;
    }
}
