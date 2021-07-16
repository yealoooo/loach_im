package cn.loach.util;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    private static final Map<String, String> tokenMap = new HashMap<String, String>(){{
        put("token", "token");
        put("token2", "token2");
        put("token3", "token3");
        put("token4", "token4");
    }};


    public static String getAppIdAndUid(String token) {
        return tokenMap.get(token);
    }

    public static boolean isUse(String token) {
        return tokenMap.containsKey(token);
    }
}
