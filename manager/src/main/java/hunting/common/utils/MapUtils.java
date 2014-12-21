package hunting.common.utils;


public class MapUtils {

	/**
	 * 调用此函数会将GCJ02坐标系LatLng转换为BD09坐标系的GeoPoint
	 * 
	 * @return BD09 坐标
	 */
	private final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	private final static double DEF_PI = 3.14159265359; // PI
	private final static double DEF_PI180 = 0.01745329252; // PI/180.0
	private final static double DEF_R = 6370693.5; // radius of earth

	/**
	 * 根据圆心、半径算出经纬度范围
	 * 
	 * @param x
	 *            圆心经度
	 * @param y
	 *            圆心纬度
	 * @param r
	 *            半径（米）
	 * @return double[4] 南侧经度left，北侧经度right，西侧纬度bottom，东侧纬度top
	 */
	public static double[] getRange(double lon, double lat, int r) {
		double[] range = new double[4];
		// 角度转换为弧度
		double ns = lat * DEF_PI180;
		double sinNs = Math.sin(ns);
		double cosNs = Math.cos(ns);
		double cosTmp = Math.cos(r / DEF_R);
		// 经度的差值
		double lonDif = Math.acos((cosTmp - sinNs * sinNs) / (cosNs * cosNs)) / DEF_PI180;
		// 保存经度
		range[0] = lon - lonDif;
		range[1] = lon + lonDif;
		double m = 0 - 2 * cosTmp * sinNs;
		double n = cosTmp * cosTmp - cosNs * cosNs;
		double o1 = (0 - m - Math.sqrt(m * m - 4 * (n))) / 2;
		double o2 = (0 - m + Math.sqrt(m * m - 4 * (n))) / 2;
		// 纬度
		double lat1 = 180 / DEF_PI * Math.asin(o1);
		double lat2 = 180 / DEF_PI * Math.asin(o2);
		// 保存
		range[2] = lat1;
		range[3] = lat2;
		return range;
	}

	/**
	 * 经纬度点是否在游戏矩形内
	 * 
	 * @param rectangle
	 *            rectangle[4] 南侧经度left，北侧经度right，西侧纬度bottom，东侧纬度top
	 * @param latlng
	 *            任意一点经纬度
	 * @return true 矩形内 false 矩形外
	 */
	public static boolean isLatlngInRectRange(double[] rectangle, float lng,float lat) {

		double x = lng;
		double y = lat;
		double leftX = rectangle[0];
		double rightX = rectangle[1];
		double bottomY = rectangle[2];
		double topY = rectangle[3];
		if ((x > leftX && x < rightX) && (y > bottomY && y < topY))
			return true;
		return false;
	}
}
