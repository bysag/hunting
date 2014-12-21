package hunting.manager.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import hunting.common.dao.GameInfoDao;
import hunting.common.oo.CurrentAccountFacade;
import hunting.common.pojo.HuntingGameInfo;
import hunting.common.utils.DateUtil;

import org.springframework.stereotype.Service;

@Service
public class GameInfoService {

    @Resource
    private GameInfoDao gameInfoDao;

    /**
     * 保存
     * @param huntingGameInfo
     * @return int -1 跟已有活动冲突  0 失败   >0成功
     */
    public int saveGameInfo(HuntingGameInfo huntingGameInfo) {
        /**
         * 结束时间小于等于开始时间 则结束时间往后延一天
         * 
         */
        Date ged = huntingGameInfo.getGameEndTime();
        Date gsd = huntingGameInfo.getGameStartTime();
        if (gsd.compareTo(ged) >= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gsd);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            huntingGameInfo.setGameEndTime(calendar.getTime());

            calendar.setTime(huntingGameInfo.getGameEndDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            huntingGameInfo.setGameEndDate(calendar.getTime());
        }

        /**
         * 判断城市,日期和时间是跟已有活动冲突
         */
        String gameStartTimeStr = DateUtil.format(huntingGameInfo.getGameStartTime(),
                DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
        int duplicateFlag = gameInfoDao.dateValidate(huntingGameInfo.getGameCity(),
                gameStartTimeStr);
        if (duplicateFlag > 0) {
            return -1;
        }

        Date date = new Date();
        huntingGameInfo.setLastModTime(date);
        huntingGameInfo.setCreatedTime(date);
        huntingGameInfo.setOperator(CurrentAccountFacade.getCurrentLoginId());
        int insertFlag = gameInfoDao.insert(huntingGameInfo);
        return insertFlag;
    }

    public HuntingGameInfo getGameInfo() {
        HuntingGameInfo hgi = null;
        hgi = gameInfoDao.getCurrentGameInfo(null);
        if (hgi == null) {
            hgi = gameInfoDao.getLastestGameInfo(null);
        }
        return hgi;
    }

    public HuntingGameInfo getGameInfo(String gameCity) {
        System.out.println("gameCity************************"+gameCity);
        HuntingGameInfo hgi = null;
        hgi = gameInfoDao.getCurrentGameInfo(gameCity);
        if (hgi == null) {
            hgi = gameInfoDao.getLastestGameInfo(gameCity);
            if(hgi==null){
                hgi = gameInfoDao.getBeforeGameInfo(gameCity); 
            }
        }
        return hgi;
    }
    
    public HuntingGameInfo getGameInfoClient(String gameCity) {
        HuntingGameInfo hgi = null;
        hgi = gameInfoDao.getCurrentGameInfo(gameCity);
        if (hgi == null) {
            hgi = gameInfoDao.getLastestGameInfo(gameCity);            
        }
        return hgi;
    }
    
    
    public int updateGameInfo(HuntingGameInfo huntingGameInfo){
        huntingGameInfo.setLastModTime(new Date());
        huntingGameInfo.setOperator(CurrentAccountFacade.getCurrentLoginId());
        return gameInfoDao.updateGameInfo(huntingGameInfo);
    }
    
    public  HuntingGameInfo getCurrentGameInfo(String gameCity){
        return gameInfoDao.getCurrentGameInfo(gameCity);
    }
    
    
}
