package hunting.manager.service;

import hunting.common.dao.GameInfoDao;
import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.PlayersDao;
import hunting.common.dao.RewardsRecordDao;
import hunting.common.dao.StatisticsGetsDao;
import hunting.common.oo.PlayerInfoFacade;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGameRewardsRecord;
import hunting.common.pojo.HuntingGameStatisticsGets;
import hunting.common.utils.DateUtil;
import hunting.quartz.RealTimeStatisticTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class StatisticsService {

    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;

    @Resource
    private StatisticsGetsDao statisticsGetsDao;

    @Resource
    private RewardsRecordDao rewardsRecordDao;

    @Resource
    private GameInfoDao gameInfoDao;

    @Resource
    private PlayersDao playersDao;

    public List<Map<String, String>> list(String gameCity, String startDate, String endDate,
            int pageSzie, int currentPage) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<Map<String, Object>> list = playerHoldLogoDao.top(gameCity, startDate, endDate,
                pageSzie, (currentPage - 1) * pageSzie);
        for (Map<String, Object> map : list) {
            double amount = Math.floor(Math.random() * 1000);
            double price = Math.floor(Math.random() * 1000);
            Map<String, String> element = new HashMap<String, String>();
            element.put("playerId", (String) map.get("playerId"));
            element.put("playerName", (String) map.get("playerId"));
            element.put("playerNickname", (Long) map.get("amount") + "");
            element.put("playerPhone", price + "");
            element.put("holdLogoAmount", (Long) map.get("amount") + "");
            element.put("holdLogoTime", (amount * price) + "");
            element.put("integral", (Long) map.get("amount") * 10 + "");
            result.add(element);
        }
        return result;
    }

    public int amount(String gameCity, String startDate, String endDate) {
        return playerHoldLogoDao.topAmount(gameCity, startDate, endDate);
    }

    public Map<String, Long> total(String gameCity, String startDate, String endDate) {
        Map<String, Long> res = new HashMap<String, Long>();
        res.put("online", playersDao.getOnlineAmount(gameCity));
        res.put("register", PlayerInfoFacade.getRegisterCount(gameCity, startDate, endDate));
        res.put("totalRegister", PlayerInfoFacade.getRegisterCount(gameCity, null, null));
        return res;
    }

    public Map<String, Object> playerDayScope(String gameCity, String playerId, String day) {
        Map<String, Object> result = new HashMap<String, Object>();
        /**
         * 获取单日最高排名
         */
        String todayStr = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD);
        if (!todayStr.equals(day)) {
            List<HuntingGameStatisticsGets> dayRes = statisticsGetsDao.list(gameCity, playerId,
                    "DAY", day);
            result.putAll(controlHistorySort(dayRes, "day", 0, 0));
        } else {
            AtomicReference<String> todaySort = RealTimeStatisticTask.getPlayerTodySortInfo().get(
                    playerId);
            String[] amountAndSort = todaySort.get().split(",");
            result.put("daySort", amountAndSort[1]);
            result.put("dayGets", amountAndSort[0]);
        }
        /**
         * 奖品
         */
        result.put("rewards", getRewardsInfo(playerId, 1000));

        return result;
    }

    private Map<String, Object> controlHistorySort(List<HuntingGameStatisticsGets> res,
            String pref, int _sort, int _gets) {
        Map<String, Object> result = new HashMap<String, Object>();
        int sort = 0;
        int gets = 0;
        if (!CollectionUtils.isEmpty(res)) {
            HuntingGameStatisticsGets sg = res.get(0);
            sort = sg.getSort();
            gets = sg.getGets();
        }
        result.put(pref + "Sort", sort > _sort ? sort : _sort);
        result.put(pref + "Gets", gets > _gets ? gets : gets);
        return result;
    }

    private String getRewardsInfo(String playerId, int limit) {
        StringBuilder sbr = new StringBuilder();
        List<HuntingGameRewardsRecord> rewards = rewardsRecordDao.listByCondition(null, null,
                playerId, limit, 0);
        float ld = 0f;
        float dj = 0f;
        if (!CollectionUtils.isEmpty(rewards)) {
            for (HuntingGameRewardsRecord rr : rewards) {

                switch (rr.getRewardsType()) {
                    case DIAN_JUAN:
                        dj += rr.getRewardsAmount();
                        break;
                    case LU_DIAN:
                        ld += rr.getRewardsAmount();
                        break;
                    default:
                        break;
                }
            }

            if (ld > 0) {
                sbr.append(ld + "绿点/");
            }
            if (dj > 0) {
                sbr.append(dj + "点劵");
            }
        }

        return sbr.toString();
    }

    public Map<String, Object> playerSelfScope(Long gameInfoId, String gameCity, String playerId) {
        Map<String, Object> result = new HashMap<String, Object>();
        /**
         * 游戏的日期
         */
        HuntingGameInfo gameInfo = gameInfoDao.getGameInfo(gameInfoId);
        List<String> dateList = new ArrayList<String>();
        if (gameInfo != null) {
            long days = (gameInfo.getGameEndDate().getTime() - gameInfo.getGameStartDate()
                    .getTime()) / (1000 * 60 * 60 * 24);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gameInfo.getGameStartDate());
            dateList.add(DateUtil.format(gameInfo.getGameStartDate(), DateUtil.PATTERN_YYYY_MM_DD));
            for (int i = 0; i < days; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String dateValue = DateUtil.format(calendar.getTime(), DateUtil.PATTERN_YYYY_MM_DD);
                if (!dateList.contains(dateValue)) dateList.add(dateValue);
            }
        }
        result.put("gameDate", dateList);

        /**
         * 总排行/当日排行
         * 
         */
        AtomicReference<String> todayInfo = RealTimeStatisticTask.getPlayerTodySortInfo().get(
                playerId);
        String daySort = "0";
        String dayGets = "0";
        String totalSort = "0";
        String totalGets = "0";
        if (todayInfo != null) {
            String[] todaySortAndGets = todayInfo.get().split(",");
            daySort = todaySortAndGets[1];
            dayGets = todaySortAndGets[0];
            AtomicReference<String> totalInfo = RealTimeStatisticTask.getPlayerTodySortInfo().get(
                    playerId);
            String[] totalSortAndGets = totalInfo.get().split(",");
            totalSort = totalSortAndGets[1];
            totalGets = totalSortAndGets[0];
        }
        result.put("daySort", daySort);
        result.put("dayGets", dayGets);
        result.put("totalSort", totalSort);
        result.put("totalGets", totalGets);
        /**
         * 获取最高排名
         */
        List<HuntingGameStatisticsGets> totalRes = statisticsGetsDao.list(gameCity, playerId,
                "TOTAL");
        result.putAll(controlHistorySort(totalRes, "hTotal", Integer.parseInt(totalSort),
                Integer.parseInt(totalGets)));

        /**
         * 获取单日最高排名
         */
        List<HuntingGameStatisticsGets> dayRes = statisticsGetsDao.list(gameCity, playerId, "DAY");
        result.putAll(controlHistorySort(dayRes, "hDay", Integer.parseInt(daySort),
                Integer.parseInt(dayGets)));
        /**
         * 奖品
         */
        result.put("rewards", getRewardsInfo(playerId, 100));
        return result;
    }

    public static void main(String[] args) {
        String startDate = "2014-10-09";
        String endDate = "2014-10-12";
        Date startDateValue = DateUtil.parse(startDate, DateUtil.PATTERN_YYYY_MM_DD);
        Date endDateValue = DateUtil.parse(endDate, DateUtil.PATTERN_YYYY_MM_DD);
        long days = (endDateValue.getTime() - startDateValue.getTime()) / (1000 * 60 * 60 * 24);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDateValue);
        for (int i = 0; i < days; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(DateUtil.format(calendar.getTime(), DateUtil.PATTERN_YYYY_MM_DD));

        }

    }
}
