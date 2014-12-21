/**
 * 
 */
package hunting.manager.service;

import java.util.Date;

import hunting.common.dao.GameInfoDao;
import hunting.common.dao.LogoMoveRecordDao;
import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.PlayersDao;
import hunting.common.init.BlackListInit;
import hunting.common.init.GameInfoInit;
import hunting.common.init.LogoMoveRecordInit;
import hunting.common.init.PlayerHoldLogoInit;
import hunting.common.init.PlayerMoveRecordInit;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGameLogoMoveRecord;
import hunting.common.pojo.HuntingGamePlayerHoldLogo;
import hunting.common.pojo.HuntingGamePlayerMoveRecord;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojoenum.PlayerHoldLogoEnum;
import hunting.common.utils.ComputeCrossBorderUtil;
import hunting.common.utils.ComputeDistanceUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 车标持有服务类
 * 
 * @author yunan.zheng
 * 
 */
@Service
public class PlayerHoldLogoService {

    private Object synObject = new Object();

    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;

    @Resource
    private GameInfoDao gameInfoDao;

    @Resource
    private PlayersDao playersDao;

    @Resource
    private LogoMoveRecordDao logoMoveRecordDao;

    public int drop(HuntingGamePlayerMoveRecord pmr) {
        HuntingGamePlayerHoldLogo phl = playerHoldLogoDao.get(pmr.getGameInfoId());
        if (phl != null && phl.getPlayerId().equals(pmr.getPlayerId())) {
            phl.setHoldType(PlayerHoldLogoEnum.UNHOLD);
            phl.setHoldLogoEndTime(new Date());
            try {
                PlayerHoldLogoInit.removePlayerHoldLogo(phl.getGameCity());
            } catch (Exception e) {
            }
            return playerHoldLogoDao.update(phl);
        }
        return 0;
    }

    /**
     * 获取车标持有者
     * 
     * @return
     */
    public String getPlayerIdBygameInfoId(long gameInfoId) {
        String holder = null;
        HuntingGamePlayerHoldLogo phl = playerHoldLogoDao.get(gameInfoId);
        if (phl != null) {
            holder = phl.getPlayerId();
        }
        return holder;
    }

    /**
     * 
     * @param pmr
     * @return -1程序异常  0 游戏已结束 ；1 已抢到；2 玩家未登陆  3 超速  4黑名单  5越界   6与车标距离超过规定距离  7本人已是持标者  8隐身期间不能抢
     *
     */
    public int get(HuntingGamePlayerMoveRecord pmr) {
        int result = -1;
        HuntingGameInfo gi = GameInfoInit.getGameInfo(pmr.getGameCity());
        if (gi == null) {
            return 0;
        }

        /**
         * 是否超过最大速度
         */
        HuntingGamePlayers beforePmr = PlayerMoveRecordInit.getPlayerInfo(pmr.getGameCity(),
                pmr.getPlayerId());
        if (beforePmr == null) {
            return 2;
        }
        double playerDistance = ComputeDistanceUtil.distance(beforePmr.getLongitude(),
                beforePmr.getLatitude(), pmr.getMoveLongtitude(), pmr.getMoveLatitude());
        if (playerDistance >= (gi.getGameMaxMoveSpeed() * 1000 * 10) / 3600) {
            return 3;
        }

        /**
         * 是否黑名单
         */
        boolean isBlacklist = BlackListInit.getBlackListSet().contains(pmr.getPlayerId());
        if (isBlacklist) {
            return 4;
        }

        /**
         * 是否出界
         */
        boolean crossBorder = ComputeCrossBorderUtil.crossBorder(gi, pmr.getMoveLongtitude(),
                pmr.getMoveLatitude());
        if (crossBorder) {
            return 5;
        }

        HuntingGameLogoMoveRecord lmr = LogoMoveRecordInit.getGameCityLogoMoveRecord(gi
                .getGameCity());
        double logoDistance = ComputeDistanceUtil.distance(lmr.getLogoLongtitude(),
                lmr.getLogoLatitude(), pmr.getMoveLongtitude(), pmr.getMoveLatitude());

        /**
         * 与车标距离是否超过规定距离
         */
        if (logoDistance >= gi.getGameGrabLogoDistance()) {
            return 6;
        }

        /**
         * 检验车标是否被人持有
         */
        synchronized (synObject) {
            //TODO需要加上事务
            HuntingGamePlayerHoldLogo phl = playerHoldLogoDao.get(pmr.getGameInfoId());
            boolean holdFlag = false;
            Date date = new Date();
            if (phl == null) {
                holdFlag = true;
            } else if (phl != null) {
                if (phl.getPlayerId().equals(pmr.getPlayerId())) {
                    return 7;
                }
                /**
                 * 隐身时间内其它玩家不能抢
                 */
                long times = date.getTime();
                if (gi.getGameEndTime().getTime() - times < gi.getGameHideTimeInvalid() * 1000
                        || times - phl.getHoldLogoStartTime().getTime() < gi.getGameHideTime() * 1000) {
                    return 8;
                } else {
                    holdFlag = true;
                }
            }
            if (holdFlag) {
                /**
                 * 上个玩家丢车标
                 */
                if (phl != null) {
                    phl.setHoldType(PlayerHoldLogoEnum.UNHOLD);
                    playerHoldLogoDao.update(phl);
                }
                /**
                 * 新玩家获取车标
                 */
                HuntingGamePlayerHoldLogo ph = new HuntingGamePlayerHoldLogo();
                ph.setGameCity(pmr.getGameCity());
                ph.setGameInfoId(pmr.getGameInfoId());
                ph.setHoldLogoEndTime(date);
                ph.setHoldLogoStartTime(date);
                ph.setHoldType(PlayerHoldLogoEnum.HOLD);
                ph.setPlayerId(pmr.getPlayerId());
                result = playerHoldLogoDao.insert(ph);

                /**
                 * 修改获取玩家的位置
                 */
                HuntingGamePlayers gp = new HuntingGamePlayers();
                gp.setCreatedTime(date);
                gp.setGameCity(pmr.getGameCity());
                gp.setGameInfoId(pmr.getGameInfoId());
                gp.setLastModTime(date);
                gp.setLatitude(pmr.getMoveLatitude());
                gp.setLongitude(pmr.getMoveLongtitude());
                gp.setOnline(true);
                gp.setPlayerId(pmr.getPlayerId());
                playersDao.update(gp);

                /**
                 * 插入修改车标的位置
                 */
                HuntingGameLogoMoveRecord newLmr = new HuntingGameLogoMoveRecord();
                newLmr.setGameCity(pmr.getGameCity());
                newLmr.setGameInfoId(pmr.getGameInfoId());
                newLmr.setLogoLatitude(pmr.getMoveLatitude());
                newLmr.setLogoLongtitude(pmr.getMoveLongtitude());
                newLmr.setCreatedTime(new Date());
                logoMoveRecordDao.insert(newLmr);
            }
        }
        return result;
    }

}
