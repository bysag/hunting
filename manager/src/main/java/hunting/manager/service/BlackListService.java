package hunting.manager.service;

import hunting.common.dao.BlackListDao;
import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.oo.CurrentAccountFacade;
import hunting.common.oo.PlayerInfoFacade;
import hunting.common.pojo.HuntingGameBlackList;
import hunting.common.pojoenum.BlackListEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class BlackListService {

    @Resource
    private BlackListDao blackListDao;
    
    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;
    
    public List<Map<String, String>> list(String gameCity,String playerId, String playerName, String playerNickname,int limit,int offset){
        List<Map<String, String>> result = new ArrayList<Map<String,String>>();
        if (!StringUtils.hasLength(playerId)&&StringUtils.hasLength(playerName)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerName(playerName);
        }

        if (!StringUtils.hasLength(playerId)&&StringUtils.hasLength(playerNickname)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerNickname(playerNickname);
        }
        List<HuntingGameBlackList> list = blackListDao.list(gameCity,playerId, limit, offset);
        if (list.isEmpty()) {
            return null;
        }
        List<String> playerIds = new ArrayList<String>();
        for (HuntingGameBlackList gbl : list) {
                playerIds.add(gbl.getPlayerId());
                double amount = Math.floor(Math.random()*1000);
                double price = Math.floor(Math.random()*1000);
                Map<String, String> element = new HashMap<String, String>();
                element.put("operator",gbl.getOperator());
                element.put("playerId",gbl.getPlayerId());
                element.put("playerName","Name ");
                element.put("playerNickname",amount+"");
                element.put("playerPhone",price+"");
                element.put("holdLogoAmount","0");
                element.put("holdLogoTime","00");
                element.put("integral","Note ");
                result.add(element);
        }
        
        /**
         * 获取用户抢标的次数
         */
        List<Map<String, Object>> playerHoldInfos = playerHoldLogoDao.getAmountByPlayerIds(playerIds);
        Map<String,Long> playerIdAmountMap = new HashMap<String, Long>();
        if (!playerHoldInfos.isEmpty()) {
            for (Map<String, Object> map : playerHoldInfos) {
                playerIdAmountMap.put((String)map.get("playerId"),(Long)map.get("amount"));
            }
            
            for (Map<String, String> resultElement : result) {
                resultElement.put("holdLogoAmount", playerIdAmountMap.get(resultElement.get("playerId"))+"");
            }
        }
        
        return result;
    }
    
    public int amount(String gameCity,String playerId, String playerName, String playerNickname){    
        
        if (!StringUtils.hasLength(playerId)&&StringUtils.hasLength(playerName)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerName(playerName);
        }

        if (!StringUtils.hasLength(playerId)&&StringUtils.hasLength(playerNickname)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerNickname(playerNickname);
        }
        return blackListDao.amount(gameCity,playerId);
    }
    
    public int add(String gameCtiy,String playerId){
        HuntingGameBlackList bl = new HuntingGameBlackList();
        Date date = new Date();
        bl.setCreatedTime(date);
        bl.setLastModTime(date);
        bl.setPlayerId(playerId);
        bl.setType(BlackListEnum.VALID);
        bl.setGameCity(gameCtiy);
        bl.setOperator(CurrentAccountFacade.getCurrentLoginId());
        List<HuntingGameBlackList> datas = new ArrayList<HuntingGameBlackList>();
        datas.add(bl);
        return blackListDao.insert(datas);
    }
    
    public int delete(String playerId){
        return blackListDao.delete(playerId);
    }
}
