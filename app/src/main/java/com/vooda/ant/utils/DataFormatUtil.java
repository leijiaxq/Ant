package com.vooda.ant.utils;

import java.text.DecimalFormat;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 17:41
 * Describe
 */

public class DataFormatUtil {
    private DataFormatUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static DecimalFormat mFormat = new DecimalFormat("0.00");

    public static DecimalFormat getFormatInstance() {
        return mFormat;
    }
}
