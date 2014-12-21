/**
 * 
 */
package hunting.manager.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hunting.common.dao.GameInfoDao;
import hunting.common.dao.LogoMoveRecordDao;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.pojo.HuntingGameLogoMoveRecord;


/**
 * @author yunan.zheng
 *
 */
@Service
public class LogoMoveRecordService {
    
    @Resource
    private LogoMoveRecordDao logoMoveRecordDao;
    @Resource
    private GameInfoDao gameInfoDao;
    
    public HuntingGameLogoMoveRecord getLastestLogoLngLat(long gameInfoId){
        HuntingGameLogoMoveRecord lmr =  logoMoveRecordDao.get(gameInfoId);
        if (lmr==null) {
           HuntingGameInfo gi = gameInfoDao.getGameInfo(gameInfoId) ;
           lmr = new HuntingGameLogoMoveRecord();
           lmr.setGameInfoId(gi.getId());
           lmr.setGameCity(gi.getGameCity());
           lmr.setLogoLatitude(gi.getGameLogoPutInPointLatitude());
           lmr.setLogoLongtitude(gi.getGameLogoPutInPointLongitude());
        }
        return lmr;
    }
}
