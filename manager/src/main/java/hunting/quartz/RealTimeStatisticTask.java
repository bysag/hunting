package hunting.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import hunting.common.dao.GameInfoDao;
import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.utils.DateUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 实时统计排名
 * 以数量来排行
 * @author yunan.zheng
 *
 */
@Service("realTimeStatiTask")
public class RealTimeStatisticTask {

    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao; 
    
    private static volatile ConcurrentHashMap<String, AtomicReference<String>> playerTodySortInfo = new ConcurrentHashMap<String, AtomicReference<String>>();
    
    private static volatile ConcurrentHashMap<String, AtomicReference<String>> playerTotalSortInfo = new ConcurrentHashMap<String, AtomicReference<String>>();


    private int limit = 200000;
    
    private int offset = 0;
    
    public static ConcurrentHashMap<String, AtomicReference<String>> getPlayerTodySortInfo(){
        return playerTodySortInfo;
    }
    
    public static ConcurrentHashMap<String, AtomicReference<String>> getPlayerTotalSortInfo(){
        return playerTotalSortInfo;
    }
    
    @Resource
    private GameInfoDao gameInfoDao;
    
    public void execute(){
        Date date = new Date();
        String today = DateUtil.format(date, DateUtil.PATTERN_YYYY_MM_DD);
        String todayTime = DateUtil.format(date, DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        List<String> citys = playerHoldLogoDao.getCity(today);
        if (!CollectionUtils.isEmpty(citys)) {
            for (String city : citys) {
                /**
                 * 当天排名
                 */
                sort(city,today+" 00:00:00", todayTime,"today");
                /**
                 * 总排名
                 */
                sort(city,"2014-01-01 00:00:00", todayTime,"total");
            }
        }
    }
    public void sort(String city,String startDate,String endDate,String type){
        List<Integer> sortList = playerHoldLogoDao.sort(city,startDate,endDate);
        List<Map<String, Object>> res =  playerHoldLogoDao.top(city, startDate,endDate, limit, offset);
        if (!CollectionUtils.isEmpty(res)) {
            for (Map<String, Object> map : res) {
                Integer amount = Integer.parseInt((Long)map.get("amount")+"");
                String playerId = (String)map.get("playerId");
                int sort = sortList.indexOf(amount);
                AtomicReference<String> value = new AtomicReference<String>();
                value.set(amount+","+(sort+1));
                if(type.equals("today")){
                    playerTodySortInfo.put(playerId, value);
                }else if(type.equals("total")){
                    playerTotalSortInfo.put(playerId, value);
                }
            }
        }  
    }
}
