package com.zq.util;

/**
 * @author zhaoqi
 * @version 1.8
 */
public class TrimUtil {
    public static String trim(String s){
        String trim = s.trim();
        String replace = trim.replace(" ", "");
        return replace  ;
    }
}
