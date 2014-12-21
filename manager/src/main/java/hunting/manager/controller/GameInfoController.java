/**
 * 
 */
package hunting.manager.controller;

import hunting.common.pojo.HuntingGameGlobalConfig;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.utils.DateUtil;
import hunting.manager.service.GameInfoService;
import hunting.manager.service.GlobalConfigService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 游戏参数
 * 
 * @author yunan.zheng
 * 
 */
@Controller
@RequestMapping("/gameinfo/")
public class GameInfoController {

    private final Logger logger = LoggerFactory.getLogger(GameInfoController.class);

    @Resource
    private GameInfoService gameInfoService;

    @Resource
    private GlobalConfigService globalConfigService;

    /**
     * 获取已有游戏的城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/getGameList", method = RequestMethod.GET)
    @ResponseBody
    public Object getGameCityList() {
        Map<String, Object> res = new HashMap<String, Object>();
        List<HuntingGameGlobalConfig> gameCityList = globalConfigService.getGameCity();
        HuntingGameInfo hgi = gameInfoService.getGameInfo();
        HuntingGameGlobalConfig gc = new HuntingGameGlobalConfig();
        gc.setConfigKey("全部");
        gc.setConfigValue("全部");
        gameCityList.add(0, gc);
        res.put("gameCity", gameCityList);
        res.put("gameInfo", hgi);
        return res;
    }

    /**
     * 添加游戏城市
     * 
     * @return
     */
    @RequestMapping(value = "/saveGameCity", method = RequestMethod.POST)
    @ResponseBody
    public Object saveGameCity(@RequestParam(value = "gameCity", required = true) String gameCity) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("result", globalConfigService.saveGameCity(gameCity));
        return res;
    }

    /**
     * 获取游戏参数
     * 
     * @param city
     * @return
     */
    @RequestMapping(value = "/getGameInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getGameInfo(@RequestParam(value = "gameCity", required = true) String gameCity) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("gameInfo", gameInfoService.getGameInfo(gameCity));
        return res;
    }

    /**
     * 设置游戏参数
     * 
     * @param city
     * @return
     */
    @RequestMapping(value = "/saveGameInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object saveGameInfo(@ModelAttribute HuntingGameInfo huntingGameInfo,
            HttpServletRequest request, HttpServletResponse response) {
        String gameStartDateStr = request.getParameter("gameStartDateStr");
        String gameStartTimeStr = request.getParameter("gameStartTimeStr");
        String gameEndDateStr = request.getParameter("gameEndDateStr");
        String gameEndTimeStr = request.getParameter("gameEndTimeStr");
        huntingGameInfo.setGameStartTime(DateUtil.parse(gameStartDateStr + " " + gameStartTimeStr,
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameStartDate(DateUtil.parse(gameStartDateStr + " 00:00:00",
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameEndTime(DateUtil.parse(gameEndDateStr + " " + gameEndTimeStr,
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameEndDate(DateUtil.parse(gameEndDateStr + " 00:00:00",
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        int res = gameInfoService.saveGameInfo(huntingGameInfo);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result", res);
        return resMap;
    }

    /**
     * 修改游戏参数
     * 
     * @param city
     * @return
     */
    @RequestMapping(value = "/updateGameInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateGameInfo(@ModelAttribute HuntingGameInfo huntingGameInfo,
            HttpServletRequest request, HttpServletResponse response) {
        String gameStartDateStr = request.getParameter("gameStartDateStr");
        String gameStartTimeStr = request.getParameter("gameStartTimeStr");
        String gameEndDateStr = request.getParameter("gameEndDateStr");
        String gameEndTimeStr = request.getParameter("gameEndTimeStr");
        huntingGameInfo.setGameStartTime(DateUtil.parse(gameStartDateStr + " " + gameStartTimeStr,
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameStartDate(DateUtil.parse(gameStartDateStr + " 00:00:00",
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameEndTime(DateUtil.parse(gameEndDateStr + " " + gameEndTimeStr,
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        huntingGameInfo.setGameEndDate(DateUtil.parse(gameEndDateStr + " 00:00:00",
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        int res = gameInfoService.updateGameInfo(huntingGameInfo);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result", res);
        return resMap;
    }

    @RequestMapping(value = "/uploadLogo", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadLogo(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("logoFile");
        String realFileName = file.getOriginalFilename();
        long times = System.currentTimeMillis();
        String uri = File.separator + "upload" + File.separator + times
                + realFileName.substring(realFileName.lastIndexOf("."), realFileName.length());
        String ctxPath = request.getSession().getServletContext().getRealPath("/");

        logger.debug("上传路径：{}", ctxPath);
        File dirPath = new File(ctxPath);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }
        File uploadFile = new File(ctxPath + uri);
        try {
            FileCopyUtils.copy(file.getBytes(), uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result", uri);
        return resMap;
    }

    @RequestMapping(value = "/saveGameRule", method = RequestMethod.POST)
    @ResponseBody
    public Object saveGameRule(@RequestParam(value = "gameCity", required = true) String gameCity,
            @RequestParam(value = "gameRule", required = true) String gameRule) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result",globalConfigService.saveGameRule(gameCity,gameRule));
        return resMap;
    }
    
    @RequestMapping(value = "/getGameRule", method = RequestMethod.GET)
    @ResponseBody
    public Object getGameRule(@RequestParam(value = "gameCity", required = true) String gameCity) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result",globalConfigService.getGameRule(gameCity));
        return resMap;
    }
}
