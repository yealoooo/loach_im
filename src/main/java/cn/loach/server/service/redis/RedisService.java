package cn.loach.server.service.redis;


import cn.loach.server.model.UserInfoModel;

public interface RedisService {
    String getUidByToken(String token);

    UserInfoModel getUserInfo(String uid);

    UserInfoModel getUserInfoByToken(String token);

    boolean existsToken(String token);
}
