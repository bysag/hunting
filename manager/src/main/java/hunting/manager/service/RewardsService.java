package hunting.manager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.RewardsRecordDao;
import hunting.common.oo.CurrentAccountFacade;
import hunting.common.oo.PlayerInfoFacade;
import hunting.common.oo.RewardsFacade;
import hunting.common.pojo.HuntingGameRewardsRecord;
import hunting.common.pojo.OOPlayerInfo;
import hunting.common.pojoenum.RewardsEnum;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RewardsService {

    @Resource
    private RewardsRecordDao rewardsRecordDao;
    
    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;

    public List<HuntingGameRewardsRecord> listByCondition(String createdTime, String operator,
            String rewarderName, String rewarderPhone, int pageSize, int currentPage) {
        String playerId = "";
        if (StringUtils.hasLength(rewarderName)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerName(rewarderName);
        }

        if (StringUtils.hasLength(rewarderPhone)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerPhone(rewarderPhone);
        }

        List<HuntingGameRewardsRecord> rrs = rewardsRecordDao.listByCondition(createdTime,
                operator, playerId, pageSize, pageSize * (currentPage-1));
        if (CollectionUtils.isEmpty(rrs)) {
            return Collections.emptyList();
        }
        List<String> playersList = new ArrayList<String>();
        for (HuntingGameRewardsRecord huntingGameRewardsRecord : rrs) {
            playersList.add(huntingGameRewardsRecord.getPlayerId());
        }

        /**
         * oo平台获取玩家姓名和手机号
         */
        Map<String, OOPlayerInfo> ooPlayers = PlayerInfoFacade
                .getPlayerInfoListByPlayerIds(playersList);
        if (!CollectionUtils.isEmpty(ooPlayers)) {
            for (HuntingGameRewardsRecord huntingGameRewardsRecord : rrs) {
                OOPlayerInfo ooplayer = ooPlayers.get(huntingGameRewardsRecord.getPlayerId());
                if (ooplayer != null) {
                    huntingGameRewardsRecord.setPlayerName(ooplayer.getPlayerName());
                    huntingGameRewardsRecord.setPlayerPhone(ooplayer.getPlayerPhone());
                }
            }
        }
        return rrs;
    }

    public int amount(String createdTime, String operator, String rewarderName, String rewarderPhone) {
        String playerId = "";
        if (StringUtils.hasLength(rewarderName)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerName(rewarderName);
        }

        if (StringUtils.hasLength(rewarderPhone)) {
            playerId = PlayerInfoFacade.getPlayerIdByPlayerPhone(rewarderPhone);
        }
        return rewardsRecordDao.amount(createdTime, operator, playerId);
    }

    /**
     * 奖励发放
     * 
     * @param startTop
     * @param endTop
     * @param rewardType
     * @return
     */
    public int reward(String gameCity,String startDate,String endDate,int startTop,int endTop,RewardsEnum rewardType,float amount) {
        /**
         * 1.获取某个排名期间的玩家
         */
        List<Map<String, Object>> topPlayers = playerHoldLogoDao.top(gameCity,startDate,endDate,(endTop-startTop),startTop-1);
        if (CollectionUtils.isEmpty(topPlayers)) return 0;
        /**
         * 2.调用OO平台接口进行发放及hunting管理后台保存发放记录
         */
        for (Map<String, Object> huntingGamePlayers : topPlayers) {
            String playerId = (String)huntingGamePlayers.get("playerId");
            try {
                boolean flag = RewardsFacade.rewards(playerId, rewardType, amount);
                if (flag) {
                    List<HuntingGameRewardsRecord> datas = new ArrayList<HuntingGameRewardsRecord>();
                    HuntingGameRewardsRecord rr = new HuntingGameRewardsRecord();
                    Date date = new Date();
                    rr.setCreatedTime(date);
                    rr.setGameCity(gameCity);
                    rr.setOperator(CurrentAccountFacade.getCurrentLoginId());
                    rr.setPlayerId(playerId);
                    rr.setRewardsAmount(amount);
                    rr.setRewardsType(rewardType);
                    rr.setRewardsId("");
                    datas.add(rr);
                    int[] res = rewardsRecordDao.insert(datas);
                    if (res.length<0) {
                        //TODO 回滚
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

}
