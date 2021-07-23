package cn.loach.server.service.redis.impl;


import cn.loach.server.constant.RedisKeyConstant;
import cn.loach.server.model.UserInfoModel;
import cn.loach.server.service.redis.RedisService;
import cn.loach.util.RedisUtil;
import com.alibaba.fastjson.JSON;

public class RedisServiceImpl implements RedisService {

    private static volatile RedisServiceImpl redisService;

    public static RedisServiceImpl getInstance() {
        if (null == redisService) {
            redisService = new RedisServiceImpl();
            synchronized (RedisServiceImpl.class) {
                if (null == redisService) {
                    redisService = new RedisServiceImpl();
                }
            }
        }
        return redisService;
    }

    @Override
    public String getUidByToken(String token) {
        return RedisUtil.get(RedisKeyConstant.AUTH_TOKEN_KEY_PRE + ":" + token);
    }

    @Override
    public UserInfoModel getUserInfo(String uid) {
        String data = RedisUtil.get(RedisKeyConstant.AUTH_USERINFO_KEY_PRE + ":" + uid);
        return JSON.parseObject(data, UserInfoModel.class);
    }

    @Override
    public UserInfoModel getUserInfoByToken(String token) {
        return getUserInfo(getUidByToken(token));
    }

    @Override
    public boolean existsToken(String token) {
        return RedisUtil.exists(RedisKeyConstant.AUTH_TOKEN_KEY_PRE + ":" + token);
    }
}
