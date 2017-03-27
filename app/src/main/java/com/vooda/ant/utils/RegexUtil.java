package com.vooda.ant.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by  leijiaxq
 * Date       2017/3/21 11:52
 * Describe   正则相关工具类
 */

public class RegexUtil {
    private RegexUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 验证手机号码
    public static boolean isMobilePhone(String mobiles) {

        Pattern p = Pattern.compile("^((14[0-9])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);
        return m.matches();

    }
    // 验证密码（6-26数字字母下划线）
    public static boolean isPassword(String mobiles) {

        Pattern p = Pattern.compile("^[0-9a-zA-Z]{6,20}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
