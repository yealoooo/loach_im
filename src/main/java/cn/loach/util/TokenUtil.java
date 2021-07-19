package cn.loach.util;

import cn.loach.server.model.UserInfoModel;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static UserInfoModel getAppIdAndUid(String token) {
        return JwtUtil.verify(token);
    }
}
