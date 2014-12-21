package hunting.manager.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hunting.common.pojoenum.RewardsEnum;
import hunting.manager.service.RewardsService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rewards/")
public class RewardsController {

    @Resource
    private RewardsService rewardsService;

    /**
     * 查询奖励发放记录
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object listByCondition(
            @RequestParam(value = "createdTime", required = false,defaultValue="") String createdTime,
            @RequestParam(value = "operator", required = false,defaultValue="") String operator,
            @RequestParam(value = "rewarderName", required = false,defaultValue="") String rewarderName,
            @RequestParam(value = "rewarderPhone", required = false,defaultValue="") String rewarderPhone,
            @RequestParam(value = "rows", required = true) int pageSize,
            @RequestParam(value = "page", required = true) int currentPage) {
        Map<String, Object> res = new HashMap<String, Object>();
        if (StringUtils.isEmpty(createdTime)&&StringUtils.isEmpty(operator)&&StringUtils.isEmpty(rewarderName)&&StringUtils.isEmpty(rewarderPhone)) {
            res.put("total", 0);
            res.put("rows",Collections.emptyList());
        }else{
            res.put("total",rewardsService.amount(createdTime, operator, rewarderName, rewarderPhone) );
            res.put("rows", rewardsService.listByCondition(createdTime, operator, rewarderName, rewarderPhone,
                    pageSize, currentPage));
        }
        return res;
    }

    /**
     * 发放奖励
     * 
     * @return
     */
    @RequestMapping(value = "/reward", method = RequestMethod.POST)
    @ResponseBody
    public Object reward(@RequestParam(value = "startTop", required = true) int startTop,
            @RequestParam(value = "endTop", required = true) int endTop,
            @RequestParam(value = "rewardType", required = true) String rewardType,
            @RequestParam(value = "gameCity", required = true) String gameCity,
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate,
            @RequestParam(value = "amount", required = true) float amount) {
        RewardsEnum rewardsEnum = null;
        if (rewardType.equals(RewardsEnum.DIAN_JUAN.name())) {
            rewardsEnum = RewardsEnum.DIAN_JUAN;
        } else if (rewardType.equals(RewardsEnum.LU_DIAN.name())) {
            rewardsEnum = RewardsEnum.LU_DIAN;
        }
        return rewardsService.reward(gameCity,startDate,endDate,startTop, endTop, rewardsEnum, amount);
    }

}
