/**
 * 
 */
package hunting.manager.service;

import hunting.common.dao.LogoMoveRecordDao;
import hunting.common.dao.PlayerHoldLogoDao;
import hunting.common.dao.PlayerMoveRecordDao;
import hunting.common.dao.PlayersDao;
import hunting.common.init.GameInfoInit;
import hunting.common.init.PlayerHoldLogoInit;
import hunting.common.init.PlayerMoveRecordInit;
import hunting.common.maptree.HuntingDtree;
import hunting.common.maptree.HuntingDtree.Box;
import hunting.common.maptree.HuntingDtree.Node;
import hunting.common.pojo.HuntingGameLogoMoveRecord;
import hunting.common.pojo.HuntingGamePlayerHoldLogo;
import hunting.common.pojo.HuntingGamePlayerMoveRecord;
import hunting.common.pojo.HuntingGamePlayers;
import hunting.common.pojoenum.PlayerHoldLogoEnum;
import hunting.common.utils.ComputeCrossBorderUtil;
import hunting.common.utils.ComputeDistanceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author yunan.zheng
 * 
 */
@Service
public class PlayerMoveRecordService {
    
    private Logger logger = LoggerFactory.getLogger(PlayerMoveRecordService.class);

    @Resource
    private PlayerMoveRecordDao playerMoveRecordDao;

    @Resource
    private PlayersDao playersDao;
    
    @Resource
    private PlayerHoldLogoDao playerHoldLogoDao;

    @Resource
    private LogoMoveRecordDao logoMoveRecordDao;
    
    private static Comparator<HuntingGamePlayerMoveRecord> distCmp = new Comparator<HuntingGamePlayerMoveRecord>() {
        @Override
        public int compare(HuntingGamePlayerMoveRecord o1,HuntingGamePlayerMoveRecord o2) {
            return NumberUtils.compare(o1.getDist(),o2.getDist());
        }
    };
    
    public Collection<HuntingGamePlayers> moveRecordList(String gameCity) {
        ConcurrentHashMap<String, HuntingGamePlayers> gameCityMoveRecord = PlayerMoveRecordInit.getGameCityMoveRecord(gameCity);
        if(gameCityMoveRecord!=null){
            return gameCityMoveRecord.values();
        }
        return Collections.emptyList();
    }
    
    public Collection<HuntingGamePlayerMoveRecord> moveRecordList(String gameCity,long gameInfoId,String lat,String lng,String distance) {
        List<HuntingGamePlayerMoveRecord> hpmrs = new ArrayList<HuntingGamePlayerMoveRecord>();
        HuntingDtree hdtree = PlayerMoveRecordInit.getHuntingTree(gameCity);
        if(hdtree!=null){
            double dlat = Double.parseDouble(lat);
            double dlng = Double.parseDouble(lng);
            double dist = Double.parseDouble(distance);
            Box box = ComputeDistanceUtil.makeBox(dlat,dlng,dist);
            List<Node> nodes = hdtree.query(box);
    
            for (Node n : nodes) {
                Float d = (float) (1000*ComputeDistanceUtil.computeDistance(dlat, dlng, n.x, n.y));
                if (d < dist) {
                    HuntingGamePlayerMoveRecord pmr = new HuntingGamePlayerMoveRecord();
                    pmr.setMoveLatitude(String.valueOf(n.x));
                    pmr.setMoveLongtitude(String.valueOf(n.y));
                    pmr.setPlayerId(n.s.getPlayerId());
                    pmr.setGameCity(n.s.getGameCtiy());
                    pmr.setGameInfoId(n.s.getGameInfoId());
                    pmr.setDist(d);
                    hpmrs.add(pmr);
                }
            }
            Collections.sort(hpmrs, distCmp);
        }
        return hpmrs;
    }

    public int saveMoveRecord(HuntingGamePlayerMoveRecord pmr) {
        /**
         * 当前是否有游戏
         */
        if(GameInfoInit.getGameInfo(pmr.getGameCity())==null){
            return -1;
        }
        /**
         * 玩家是车标持有者
         */
        HuntingGamePlayerHoldLogo phl = PlayerHoldLogoInit.getPlayerHoldLogo(pmr.getGameCity());
        if(phl!=null&&phl.getPlayerId().equals(pmr.getPlayerId())){
            /**
             * 出界，获取上一次玩家的地址为车标地址
             */
            String lng = pmr.getMoveLongtitude();
            String lat = pmr.getMoveLatitude(); 
            boolean crossBorder = ComputeCrossBorderUtil.crossBorder(GameInfoInit.getGameInfo(pmr.getGameCity()), lng, lat);
            if(crossBorder){
               HuntingGamePlayers hgp = playersDao.get(pmr.getPlayerId(),pmr.getGameInfoId());
               lat = hgp.getLatitude();
               lng = hgp.getLongitude();
               /**
                * 丢下车标
                */
               phl.setHoldType(PlayerHoldLogoEnum.UNHOLD);
               phl.setHoldLogoEndTime(new Date());
               playerHoldLogoDao.update(phl);
               try {
                   if(StringUtils.isNotBlank(pmr.getGameCity())){
                       PlayerHoldLogoInit.removePlayerHoldLogo(pmr.getGameCity());
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            /**
             * 插入车标位置
             */
            HuntingGameLogoMoveRecord lmr = new HuntingGameLogoMoveRecord();
            lmr.setCreatedTime(new Date());
            lmr.setLogoLatitude(lat);
            lmr.setLogoLongtitude(lng);
            lmr.setGameCity(pmr.getGameCity());
            lmr.setGameInfoId(pmr.getGameInfoId());
            logoMoveRecordDao.insert(lmr);
        }
        
        /**
         * 修改玩家最后移动地址
         */
        HuntingGamePlayers gp = new HuntingGamePlayers();
        Date date = new Date();
        gp.setCreatedTime(date);
        gp.setGameCity(pmr.getGameCity());
        gp.setGameInfoId(pmr.getGameInfoId());
        gp.setLastModTime(date);
        gp.setLatitude(pmr.getMoveLatitude());
        gp.setLongitude(pmr.getMoveLongtitude());
        gp.setOnline(true);
        gp.setPlayerId(pmr.getPlayerId());
        return  playersDao.update(gp);
    }
    
   
    /**
     * 登录
     * @param pmr
     * @return
     */
    public int login(HuntingGamePlayerMoveRecord pmr){
        /**
         * 修改玩家在线状态
         */
        HuntingGamePlayers gp = new HuntingGamePlayers();
        Date date = new Date();
        gp.setLastModTime(date);
        gp.setLatitude(pmr.getMoveLatitude());
        gp.setLongitude(pmr.getMoveLongtitude());
        gp.setOnline(true);
        gp.setPlayerId(pmr.getPlayerId());  
        gp.setGameCity(pmr.getGameCity());
        gp.setGameInfoId(pmr.getGameInfoId());
        gp.setCreatedTime(date);
        int result = 0;
        try{
            result = playersDao.update(gp);
            if (result == 0) {
                result = playersDao.insert(gp);
            }
        }catch(Exception e){
           logger.error(" e {}",e); 
        }
        return result;
    }
    
    
    /**
     * 註銷
     * @param pmr
     * @return
     */
    public int logout(HuntingGamePlayerMoveRecord pmr){
        /**
         * 退出玩家是车标持有者
         */
        HuntingGamePlayerHoldLogo phl = playerHoldLogoDao.get(pmr.getGameInfoId());
        if(phl!=null&&phl.getPlayerId().equals(pmr.getPlayerId())){
            /**
             *更新车标位置
             */
            HuntingGameLogoMoveRecord lmr = new HuntingGameLogoMoveRecord();
            lmr.setCreatedTime(new Date());
            lmr.setLogoLatitude(pmr.getMoveLatitude());
            lmr.setLogoLongtitude(pmr.getMoveLongtitude());
            lmr.setGameCity(pmr.getGameCity());
            lmr.setGameInfoId(pmr.getGameInfoId());
            logoMoveRecordDao.insert(lmr);
            /**
             * 退出玩家是车标持有者,丢下车标
             */
            phl.setHoldType(PlayerHoldLogoEnum.UNHOLD);
            phl.setHoldLogoEndTime(new Date());
            playerHoldLogoDao.update(phl);
            
            PlayerHoldLogoInit.removePlayerHoldLogo(pmr.getGameCity());
        }
        
        /**
         * 修改玩家在线状态
         */
        HuntingGamePlayers gp = new HuntingGamePlayers();
        Date date = new Date();
        gp.setLastModTime(date);
        gp.setLatitude(pmr.getMoveLatitude());
        gp.setLongitude(pmr.getMoveLongtitude());
        gp.setOnline(false);
        gp.setPlayerId(pmr.getPlayerId());    
        gp.setGameInfoId(pmr.getGameInfoId());
        return playersDao.update(gp);
    }
}
