package hunting.client.response;

import java.util.Date;

import hunting.common.utils.DateUtil;
import hunting.manager.service.StatisticsService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yunan.zheng
 * 
 */
@Controller
@RequestMapping("/statistics/")
public class DataStatisticsController {
    
    @Resource
    private StatisticsService statisticsService;

    @RequestMapping(value = "/todaytop10", method = RequestMethod.GET)
    @ResponseBody
    public Object todayTop10(@RequestParam(value = "city", required = true) String gameCity) {
        if(StringUtils.isBlank(gameCity)) return "";
        String startDate = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD)+" 00:00:00";
        String endDate = DateUtil.format(new Date(), DateUtil.PATTERN_YYYY_MM_DD)+" 23:59:59";
        return statisticsService.list(gameCity, startDate, endDate, 10, 1);
    }

    @RequestMapping(value = "/totaltop10", method = RequestMethod.GET)
    @ResponseBody
    public Object totalTop10(@RequestParam(value = "city", required = true) String gameCity) {
        if(StringUtils.isBlank(gameCity)) return "";
        System.out.println("TEST....");
        return statisticsService.list(gameCity, null, null, 10, 1);
    }

    @RequestMapping(value = "/selfScope", method = RequestMethod.GET)
    @ResponseBody
    public Object playerSelfScope(
            @RequestParam(value = "id", required = true) long gameInfoId,
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "playerId", required = true) String playerId) {
        if(StringUtils.isBlank(gameCity)||StringUtils.isBlank(playerId)) return "";
        return statisticsService.playerSelfScope(gameInfoId,gameCity, playerId);
    }
    
    @RequestMapping(value = "/dayScope", method = RequestMethod.GET)
    @ResponseBody
    public Object playerDayScope(
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "playerId", required = true) String playerId,
            @RequestParam(value = "date", required = true) String date) {
        if(StringUtils.isBlank(gameCity)||StringUtils.isBlank(playerId)||StringUtils.isBlank(date)) return "";
        return  statisticsService.playerDayScope(gameCity, playerId,date);
    }
}
