package hunting.manager.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import hunting.manager.service.StatisticsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics/")
public class StatisticsController {
    
    @Resource
    private StatisticsService statisticsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(value = "city" ,required=false, defaultValue="") String city,
            @RequestParam(value = "startDate", required=false, defaultValue="") String startDate,
            @RequestParam(value = "endDate", required=false, defaultValue="") String endDate,
            @RequestParam(value = "rows", required = true) int pageSize,
            @RequestParam(value = "page", required = true) int currentPage
           ){
        Map<String, Object> res = new HashMap<String, Object>();
        if (StringUtils.isEmpty(city)&&StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)) {
            res.put("total", 0);
            res.put("rows",Collections.emptyList());
        }else{
            res.put("total", statisticsService.amount(city, startDate, endDate));
            res.put("rows", statisticsService.list(city, startDate, endDate,pageSize,currentPage));
        }
        return  res;
    } 

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @ResponseBody
    public Object total(
            @RequestParam(value = "city" ,required=false, defaultValue="") String city,
            @RequestParam(value = "startDate", required=false, defaultValue="") String startDate,
            @RequestParam(value = "endDate", required=false, defaultValue="") String endDate           
           ){
        return statisticsService.total(city, startDate, endDate);
    }
}
