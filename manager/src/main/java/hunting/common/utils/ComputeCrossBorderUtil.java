package hunting.common.utils;

import hunting.common.pojo.HuntingGameInfo;

/**
 * 计算是否越界
 * @author yunan.zheng
 *
 */
public class ComputeCrossBorderUtil {

    public static boolean crossBorder(HuntingGameInfo gi,String currentLng,String currentLat){
        double[] rectangle = MapUtils.getRange(Double.parseDouble(gi.getGameCenterLongitude()), Double.parseDouble(gi.getGameCenterLatitude()), Integer.parseInt(gi.getGameCenterRadius())*1000);
        if(MapUtils.isLatlngInRectRange(rectangle, Float.parseFloat(currentLng), Float.parseFloat(currentLat))){
            return false;
        }else{
            return true;
        }
        /**
        double distance = ComputeDistanceUtil.distance( gi.getGameCenterLatitude(),gi.getGameCenterLongitude(), currentLat,currentLng);
        if(distance>Double.parseDouble(gi.getGameCenterRadius())*1000){
            return true;
        }else{
            return false;
        }
        */
    }
}
