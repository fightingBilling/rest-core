package com.somnus.rest.core.support;

import java.math.BigDecimal;

/**
 * RoundUtils
 *
 * @author levis
 * @version 1.0 14-2-13
 */
public  class RoundUtils {
    /**
     * 四舍五入
     *
     * @param number
     * @param scale
     * @return
     */
    public static BigDecimal round(BigDecimal number,int scale){
        return number.setScale(scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 上入
     *
     * @param number
     * @param scale
     * @return
     */
    public static BigDecimal roundUp(BigDecimal number,int scale){
        return number.setScale(scale,BigDecimal.ROUND_UP);
    }

    /**
     * 下舍
     *
     * @param number
     * @param scale
     * @return
     */
    public static BigDecimal roundDown(BigDecimal number,int scale){
        return number.setScale(scale,BigDecimal.ROUND_DOWN);
    }
}
