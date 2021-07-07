package cn.loach.util;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str) || str.length() == 0;
    }

    public static boolean isEmpty(String... strArray){
        for (String str : strArray) {
            if (isEmpty(str)){
                return true;
            }
        }
        return false;
    }
}
