package hunting.common.utils;

import hunting.common.maptree.HuntingDtree.Box;

/**
 * 计算两个点之间的距离
 * @author yunan.zheng
 *
 */
public class ComputeDistanceUtil {
    private static final double EARTH_RADIUS = 6378.137;
    private static final double PI = 3.1415926535897;

    public static double rad(double d) {
        return d * PI / 180.0;
    }

    public static double computeDistance(double lat1, double lng1, double lat2,
            double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;

        return s;
    }

    public static Box makeBox(float x, float y, float distance) {
        float r = distance / MAX_L2M;
        return new Box(x - r, y - r, x + r, y + r);
    }
    
    public static Box makeBox(double x, double y, double distance) {
        double r = distance / MAX_L2M;
        return new Box(x - r, y - r, x + r, y + r);
    }
    
    private static final float MAX_L2M = 55000.0f;
    
    //返回米
    public static double distance(String logoLng,String logoLat,String playerLng,String playerLat){
        return 1000*computeDistance(Double.parseDouble(logoLat),Double.parseDouble(logoLng),Double.parseDouble(playerLat),Double.parseDouble(playerLng));
    }
    
    public static void main(String[] args) {
        System.out.println(computeDistance(40.064421,116.421207,40.064425,116.421215)*1000);
        System.out.println((80*1000*10)/3600);
    }
}
