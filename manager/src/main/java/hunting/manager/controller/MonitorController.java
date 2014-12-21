package hunting.manager.controller;

import java.util.HashMap;
import java.util.Map;

import hunting.manager.service.MonitorService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor/")
public class MonitorController {
    
    //private final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Resource
    private MonitorService monitorService;
    
    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    @ResponseBody
    public String locations(@RequestParam(value = "city", required = true) long gameInfoId) {
        
        return null;
    }
       

    /**
     * 查找城市
     * @return
     */
    @RequestMapping(value = "/searchGameCity", method = RequestMethod.GET)
    @ResponseBody
    public Object searchGameCity(@RequestParam(value = "city", required = true) String gameCity){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("result", monitorService.searchGameCity(gameCity));
        return res;
    }
    
    /**
     * 查找玩家
     * @return
     */
    @RequestMapping(value = "/searchPlayer", method = RequestMethod.GET)
    @ResponseBody
    public Object searchPlayer(@RequestParam(value = "playerId", required = true) String playerId,@RequestParam(value = "gameInfoId", required = true) long gameInfoId){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("result", monitorService.searchPlayer(playerId,gameInfoId));
        return res;
    }
    
    

}
