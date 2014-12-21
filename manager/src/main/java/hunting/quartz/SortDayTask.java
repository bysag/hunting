package hunting.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.StatisticsGetsDao;
import hunting.common.pojo.HuntingGameStatisticsGets;
import hunting.common.utils.DateUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 算玩家每一天的排名
 * @author yunan.zheng
 *
 */
@Service("sortDayTask")
public class SortDayTask {
    
    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;
    
    @Resource
    private StatisticsGetsDao statisticsGetsDao;
    
    public void execute(){        
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        String yesterDayStr = DateUtil.format(yesterday.getTime(), DateUtil.PATTERN_YYYY_MM_DD);
        List<String> citys = playerHoldLogoDao.getCity(yesterDayStr);
        if (!CollectionUtils.isEmpty(citys)) {
            int limit = 100000;
            int offset = 0;
            for (String city : citys) {
                List<Integer> sortList = playerHoldLogoDao.sort(city, yesterDayStr+" 00:00:00", yesterDayStr+" 23:59:59");
                List<Map<String, Object>> res =  playerHoldLogoDao.top(city, yesterDayStr+" 00:00:00", yesterDayStr+" 23:59:59", limit, offset);
                if (!CollectionUtils.isEmpty(res)) {
                    List<HuntingGameStatisticsGets> insertList = new ArrayList<HuntingGameStatisticsGets>();
                    Date createTime = new Date();
                    for (Map<String, Object> map : res) {
                        HuntingGameStatisticsGets sg = new HuntingGameStatisticsGets();
                        sg.setCreatedTime(createTime);
                        sg.setGameCity(city);
                        sg.setGets(Integer.parseInt((Long)map.get("amount")+""));
                        sg.setPlayerId((String)map.get("playerId"));
                        sg.setSortDate(DateUtil.parse(yesterDayStr,DateUtil.PATTERN_YYYY_MM_DD));
                        sg.setSort(sortList.indexOf(sg.getGets())+1);
                        sg.setType("DAY");
                        insertList.add(sg);
                    }
                    if (!CollectionUtils.isEmpty(insertList)) {
                        statisticsGetsDao.insert(insertList);
                    }
                }
            }
        }
    }
}
