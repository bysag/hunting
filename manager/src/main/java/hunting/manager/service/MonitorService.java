/**
 * 
 */
package hunting.manager.service;

import hunting.common.oo.PlayerInfoFacade;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGameLogoMoveRecord;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojo.OOPlayerInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author yunan.zheng
 * 
 */
@Service
public class MonitorService {

    @Resource
    private GameInfoService gameInfoService;

    @Resource
    private PlayerHoldLogoService playerHoldLogoService;

    @Resource
    private GamePlayerService gamePlayerService;

    @Resource
    private LogoMoveRecordService logoMoveRecordService;

    /**
     * 查找城市
     */
    public Map<String, Object> searchGameCity(String gameCity) {
        Map<String, Object> res = new HashMap<String, Object>();

        /**
         * 1.统计数据：在线玩家人数、注册玩家人数、车标持有者
         */

        HuntingGameInfo hgi = gameInfoService.getCurrentGameInfo(gameCity);
        if (hgi == null) {
            return null;
        }

        res.put("gameInfo", hgi);

        long gameInfoId = hgi.getId();
        String holder = playerHoldLogoService.getPlayerIdBygameInfoId(gameInfoId);
        res.put("holder", holder);

        res.put("registerAmount", PlayerInfoFacade.getRegisterCount(gameCity,null,null));

        res.put("onlineAmount", gamePlayerService.getOnlineAmount(gameCity));

        /**
         * 2.车标最后坐标,参与玩家的坐标
         */
        HuntingGameLogoMoveRecord lmr = logoMoveRecordService.getLastestLogoLngLat(gameInfoId);
        res.put("logoLngLat", lmr);

        List<HuntingGamePlayers> hgps = gamePlayerService.getAllPlayers(gameInfoId);

        res.put("playerLngLat", hgps);

        return res;
    }
    /**
     * 
     * @param gameCity
     * @return
     */
    public OOPlayerInfo searchPlayer(String playerId,long gameInfoId) {
        OOPlayerInfo player = gamePlayerService.getPlayerByPlayerId(playerId,gameInfoId);
        if(player!=null){
            List<String> playerIds = new ArrayList<String>();
            playerIds.add(playerId);
            Map<String, OOPlayerInfo> res = PlayerInfoFacade.getPlayerInfoListByPlayerIds(playerIds);
            if(!CollectionUtils.isEmpty(res)){
                OOPlayerInfo ooPlayer = res.get(playerId);
                if(ooPlayer!=null){
                    player.setPlayerName(ooPlayer.getPlayerName());
                    player.setPlayerPhone(ooPlayer.getPlayerPhone());
                    player.setPlayerNickname(ooPlayer.getPlayerNickname());
                }
            }
        }
        return player;
    }

}
