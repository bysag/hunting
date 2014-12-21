package hunting.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期处理工具类。
 * 
 * @author yunan.zheng
 */
public final class DateUtil {

    /** Logger */
    protected static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    // Patterns
    /** 1970 */
    public static final String PATTERN_YYYY = "yyyy";
    /** 197001 */
    public static final String PATTERN_YYYYMM = "yyyyMM";
    /** 1970-01 */
    public static final String PATTERN_YYYY_MM = "yyyy-MM";
    /** 19700101 */
    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    /** 1970-01-01 */
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    /** 19700101010101 */
    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /** 1970-01-01 01:01:01 */
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** 1970-01-01 01:01:01.123 */
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 获取系统时间
     * 
     * @return 系统时间
     */
    public static java.util.Date getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取日期时间。
     * 
     * @param source 日期时间字符串
     * @return 日期时间
     */
    public static java.util.Date parse(String source) {

        String pattern = "";

        switch (source.length()) {
        case 4:
            pattern = PATTERN_YYYY;
            break;
        case 6:
            pattern = PATTERN_YYYYMM;
            break;
        case 7:
            pattern = PATTERN_YYYY_MM;
            break;
        case 8:
            pattern = PATTERN_YYYYMMDD;
            break;
        case 10:
            pattern = PATTERN_YYYY_MM_DD;
            break;
        case 14:
            pattern = PATTERN_YYYYMMDDHHMMSS;
            break;
        case 19:
            pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
            break;
        case 23:
            pattern = PATTERN_YYYY_MM_DD_HH_MM_SS_SSS;
            break;
        }

        return parse(source, pattern);
    }

    /**
     * 获取日期时间。
     * 
     * @param source 日期时间字符串
     * @param pattern 字符串格式
     * @return 日期时间
     */
    public static java.util.Date parse(String source, String pattern) {

        if (source == null || source.length() == 0 || pattern == null || pattern.length() == 0) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        java.util.Date ret = null;
        try {
            ret = format.parse(source);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 格式化时间
     * 
     * @param date 日期
     * @param pattern 格式
     * @return 日期字符
     */
    public static String format(Date date, String pattern) {

        if (date == null || pattern == null || pattern.length() == 0) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);

        return format.format(date);
    }
    
    /**
     * 获取当天日期，只到天
     * @return
     */
    public static Date getCurrentDayWithoutTime(){
		String opTimeStr = format(new Date(), PATTERN_YYYY_MM_DD);
    	return  parse(opTimeStr,PATTERN_YYYY_MM_DD);
    }
    
    
}
