package hunting.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import hunting.common.init.GameInfoInit;
import hunting.common.init.LogoMoveRecordInit;
import hunting.common.init.PlayerHoldLogoInit;
import hunting.common.init.PlayerMoveRecordInit;
import hunting.common.pojo.HuntingGameInfo;

/**
 * 清除内存数据定时任务
 * @author yunan.zheng
 *
 */
@Service("clearMemoryDataTask")
public class ClearMemoryDataTask {

    public void execute(){
        ConcurrentHashMap<String, HuntingGameInfo> gameInfos = GameInfoInit.getGameInfos();
        Set<String> citys = gameInfos.keySet();
        long times = System.currentTimeMillis();
        List<String> removeList = new ArrayList<String>();
        for (String city : citys) {
            HuntingGameInfo gi = gameInfos.get(city);
            if(gi.getGameEndTime().getTime()<times){
                removeList.add(city);
            }
        }
        
        for (String removeCity : removeList) {
            GameInfoInit.removeGameInfos(removeCity);
            LogoMoveRecordInit.removeLogoMoveRecord(removeCity);
            PlayerHoldLogoInit.removePlayerHoldLogo(removeCity);
            PlayerMoveRecordInit.remvoeMoveRecord(removeCity);
        }
    }
}
