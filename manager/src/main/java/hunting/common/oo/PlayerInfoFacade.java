package hunting.common.oo;

import hunting.common.pojo.OOPlayerInfo;

import java.util.List;
import java.util.Map;

/**
 * 获取OO平台的用户信息
 * @author yunan.zheng
 *
 */
public class PlayerInfoFacade {
   
    /**
     * 获取注册人数
     * @param gameInfoId
     * @return
     */
    public static long getRegisterCount(String city,String startDate,String endDate){
        return 1000;
    }
    
    /**
     * 根据玩家名字查找玩家编号
     * @return
     */
    public static  String getPlayerIdByPlayerName(String playerName){
        return null;
    }
  
    /**
     * 根据玩家名字查找玩家编号
     * @param playerPhone
     * @return
     */
    public static String getPlayerIdByPlayerPhone(String playerPhone){
        return null;
    }
    
    /**
     * 根据玩家名字查找玩家编号
     * @param playerPhone
     * @return
     */
    public static String getPlayerIdByPlayerNickname(String PlayerNickname){
        return null;
    }
    
    /**
     * 根据玩家编号获取玩家的详情
     * @param playerIds
     * @return
     */
    public static Map<String,OOPlayerInfo> getPlayerInfoListByPlayerIds(List<String> playerIds){
        return null;
    }
    
    
    
}
