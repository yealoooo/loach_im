package cn.loach.util;

import cn.loach.server.model.UserInfoModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;


public class JwtUtil {
    private static final String TOKEN_SECRET="DD5654D654DSD5S1D65S4D65S1D";

    public static UserInfoModel verify(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setUid(jwt.getClaim("uid").asString());
            userInfoModel.setNickName(jwt.getClaim("nickName").asString());
            userInfoModel.setAvatar(jwt.getClaim("avatar").asString());
            return userInfoModel;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
