package hunting.client.response;

import java.util.HashMap;
import java.util.Map;

import hunting.common.init.GameInfoInit;
import hunting.common.pojo.HuntingGameInfo;
import hunting.manager.service.GameInfoService;
import hunting.manager.service.GlobalConfigService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/game/")
public class GameController {
    
    @Resource
    private GameInfoService gameInfoService;
    
    @Resource
    private GlobalConfigService globalConfigService;
    
    /**
     * 获取城市的游戏信息
     * 
     * @param gameCity
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object get( @RequestParam(value = "city",required = true) String gameCity){
        if (StringUtils.isBlank(gameCity)) {
            return "city is not blank";
        }
        HuntingGameInfo gi = GameInfoInit.getGameInfo(gameCity);
        if (gi==null) {
           gi = gameInfoService.getGameInfoClient(gameCity) ;
        }
        return gi;
    }
    
    /**
     * 获取城市的游戏协议
     * 
     * @param gameCity
     * @return
     */
    @RequestMapping(value = "/rule", method = RequestMethod.GET)
    @ResponseBody
    public Object rule( @RequestParam(value = "city",required = true) String gameCity){
        if (StringUtils.isBlank(gameCity)) {
            return "city is not blank";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", globalConfigService.getGameRule(gameCity));
        return result;
    }    
}
