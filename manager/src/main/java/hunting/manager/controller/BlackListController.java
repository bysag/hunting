package hunting.manager.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import hunting.manager.service.BlackListService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 黑名单列表管理
 * 
 * @author yunan.zheng
 * 
 */
@Controller
@RequestMapping("/blacklist/")
public class BlackListController {

    @Resource
    private BlackListService blackListService;

    /**
     * 获取黑名单列表
     * 
     * @param gameInfoId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(value = "gameCity", required = false,defaultValue="") String gameCity,
            @RequestParam(value = "playerId", required = false,defaultValue="") String playerId,
            @RequestParam(value = "playerName", required = false,defaultValue="") String playerName,
            @RequestParam(value = "playerNickname", required = false,defaultValue="") String playerNickname,
            @RequestParam(value = "rows", required = true) int pageSize,
            @RequestParam(value = "page", required = true) int currentPage) {
        Map<String, Object> res = new HashMap<String, Object>();
 
            int amount = blackListService.amount(gameCity,playerId,playerName,playerNickname);
            List<Map<String, String>> result = null;//new ArrayList<Map<String,String>>();
            if (amount>0) {
                result = blackListService.list(gameCity,playerId,playerName,playerNickname,pageSize, (currentPage-1)*pageSize);
            }
            res.put("total", amount);
            res.put("rows",result);
        return res;
    }

    /**
     * 删除黑名单
     * 
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@RequestParam(value = "playerId", required = true) String playerId) {
        int res = blackListService.delete(playerId);
        return res;
    }

    /**
     * 添加黑名单
     * 
     * @param gameInfoId
     * @param playerId
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestParam(value = "city", required = true) String city,
            @RequestParam(value = "playerId", required = true) String playerId) {
        int res = blackListService.add(city,playerId);
        return res;
    }
}
