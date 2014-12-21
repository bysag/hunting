package hunting.client.response;

import hunting.common.init.LogoMoveRecordInit;
import hunting.common.init.PlayerHoldLogoInit;
import hunting.common.pojo.HuntingGamePlayerHoldLogo;
import hunting.common.pojo.HuntingGamePlayerMoveRecord;
import hunting.common.pojo.OOPlayerInfo;
import hunting.manager.service.GamePlayerService;
import hunting.manager.service.PlayerMoveRecordService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 */
@Controller
@RequestMapping("/location/")
public class MoveController {

    @Resource
    private PlayerMoveRecordService playerMoveRecordService;
    
    @Resource
    private GamePlayerService gamePlayerService;

    /**
     * 保存玩家移动的位置
     * 
     * @param gameInfoId
     * @param gameCity
     * @param playerId
     * @param longtitude
     * @param latitude
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public Object savePlayerMoveLocation(
            @RequestParam(value = "id", required = true) long gameInfoId,
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
        result.put("result", playerMoveRecordService.saveMoveRecord(pmr));
        HuntingGamePlayerHoldLogo holder = PlayerHoldLogoInit.getPlayerHoldLogo(gameCity);
        String logoHolder = "";
        long holdTime = 0;
        if (holder != null) {
            logoHolder = holder.getPlayerId();
            holdTime = holder.getHoldLogoStartTime().getTime();
        }
        result.put("logoHolder", logoHolder);
        result.put("logoTime", holdTime);
        result.put("logoInfo",LogoMoveRecordInit.getGameCityLogoMoveRecord(gameCity));
        return result;
    }

    /**
     * 位置数据
     * 
     * @param gameInfoId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object locationList(@RequestParam(value = "id", required = true) long gameInfoId,
            @RequestParam(value = "city", required = true) String gameCity) {
        if (gameInfoId == 0 || StringUtils.isBlank(gameCity)
             ) {
            return "";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("logo", LogoMoveRecordInit.getGameCityLogoMoveRecord(gameCity));
        result.put("players", playerMoveRecordService.moveRecordList(gameCity));
        return result;
    }

    /**
     * 位置数据
     * 
     * @param gameInfoId
     * @return
     */
    @RequestMapping(value = "/listByDist", method = RequestMethod.GET)
    @ResponseBody
    public Object locationList(@RequestParam(value = "id", required = true) long gameInfoId,
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "lat", required = true) String lat,
            @RequestParam(value = "lon", required = true) String lng,
            @RequestParam(value = "dist", required = true) String distance) {
        if (gameInfoId == 0 || StringUtils.isBlank(gameCity)
                || StringUtils.isBlank(lat) || StringUtils.isBlank(lng)
                || StringUtils.isBlank(distance)) {
            return "";
        }
        return playerMoveRecordService.moveRecordList(gameCity, gameInfoId, lat, lng, distance);
    }

    /**
     * 登录
     * 
     * @param gameInfoId
     * @param gameCity
     * @param playerId
     * @param longtitude
     * @param latitude
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public Object init(@RequestParam(value = "id", required = true) long gameInfoId,
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
        result.put("result", playerMoveRecordService.login(pmr));
        return result;
    }

    @RequestMapping(value = "/isRegister", method = RequestMethod.GET)
    @ResponseBody
    public Object isRegister(@RequestParam(value = "id", required = true) long gameInfoId,
            @RequestParam(value = "city", required = true) String gameCity,
            @RequestParam(value = "playerId", required = true) String playerId
          ) {
        if (gameInfoId == 0 || StringUtils.isBlank(gameCity)
                || StringUtils.isBlank(playerId)) {
            return "";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        OOPlayerInfo ooPlayerInfo = gamePlayerService.getPlayerByPlayerId(playerId,gameInfoId);
        boolean val = false;
        if(ooPlayerInfo!=null){
            if(ooPlayerInfo.getGameInfoId()==gameInfoId){
                val = true;
            }
        }
        result.put("result",val);
        return result;
    }
    /**
     * 注销
     * 
     * @param gameInfoId
     * @param gameCity
     * @param playerId
     * @param longtitude
     * @param latitude
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(@RequestParam(value = "id", required = true) long gameInfoId,
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
        result.put("result", playerMoveRecordService.logout(pmr));
        return result;
    }

}
