package hunting.client.response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hunting.common.pojo.HuntingGamePlayerMoveRecord;
import hunting.manager.service.PlayerHoldLogoService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 位置请求
 * 
 * @author yunan.zheng
 * 
 */
@Controller
@RequestMapping("/logo/")
public class LogoController {

    @Resource
    private PlayerHoldLogoService playerHoldLogoService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@RequestParam(value = "id", required = true,defaultValue="0") long gameInfoId,
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "playerId", required = true) String playerId,
            @RequestParam(value = "lon", required = true) String longtitude,
            @RequestParam(value = "lat", required = true) String latitude) {
        if (gameInfoId == 0 || StringUtils.isBlank(gameCity)
                || StringUtils.isBlank(longtitude) || StringUtils.isBlank(latitude)
                || StringUtils.isBlank(playerId)) {
            return "";
        }
        HuntingGamePlayerMoveRecord pmr = new HuntingGamePlayerMoveRecord();
        pmr.setGameCity(gameCity);
        pmr.setGameInfoId(gameInfoId);
        pmr.setMoveLatitude(latitude);
        pmr.setMoveLongtitude(longtitude);
        pmr.setPlayerId(playerId);
        pmr.setCreated(new Date());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", playerHoldLogoService.get(pmr));
        return result;
    }

    @RequestMapping(value = "/drop", method = RequestMethod.GET)
    @ResponseBody
    public Object drop(@RequestParam(value = "id", required = true) long gameInfoId,
            @RequestParam(value = "playerId", required = true) String playerId,
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "lon", required = true) String longtitude,
            @RequestParam(value = "lat", required = true) String latitude) {
        if (gameInfoId == 0 || StringUtils.isBlank(gameCity)
                || StringUtils.isBlank(longtitude) || StringUtils.isBlank(latitude)
                || StringUtils.isBlank(playerId)) {
            return "";
        }
        HuntingGamePlayerMoveRecord pmr = new HuntingGamePlayerMoveRecord();
        pmr.setGameCity(gameCity);
        pmr.setGameInfoId(gameInfoId);
        pmr.setMoveLatitude(latitude);
        pmr.setMoveLongtitude(longtitude);
        pmr.setPlayerId(playerId);
        pmr.setCreated(new Date());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", playerHoldLogoService.drop(pmr));
        return result;
    }
}
