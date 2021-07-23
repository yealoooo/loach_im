package cn.loach.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Map;

public class RedisUtil {

    private static final JedisPool jedisPool;

//    private static String password = "xxxxxxxxxx";

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxIdle(10);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 获取jedis
     *
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
//        jedis.auth(password);
        return jedis;
    }

    /**
     * jedis放回连接池
     *
     * @param jedis
     */
    public static void close(Jedis jedis) {
        //从源码可以分析得到，如果是使用连接池的形式，这个并非真正的close,而是把连接放回连接池中
        if (jedis != null) {
            jedis.close();
        }
    }

    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        } finally {
            close(jedis);
        }
    }

    /**
     * get
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        } finally {
            close(jedis);
        }
    }

    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        } finally {
            close(jedis);
        }
    }

    public static void set(String key, String value, long seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value, SetParams.setParams().ex(seconds));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        } finally {
            close(jedis);
        }
    }


    public static Long incr(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static void hset(String key,String field,String value){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key,field,value);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static String hget(String key,String field){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key,field);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(jedis);
        }
        return null;
    }

    public static Map<String,String> hgetAll(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    /**
     *
     * @param timeout 0表示永久 单位秒
     * @param key key
     * @return [key,value]
     */
    public static String blpop(int timeout,String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.blpop(timeout, key);
            return list.get(1);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static String blpop(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.blpop(0, key);
            return list.get(1);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static void lpush(String key,String... value){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lpush(key,value);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }
    }

    /**
     *
     * @param timeout 0表示永久 单位秒
     * @param key key
     * @return [key,value]
     */
    public static String brpop(int timeout,String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.brpop(timeout, key);
            return list.get(1);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static String brpop(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.brpop(0, key);
            return list.get(1);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }finally {
            close(jedis);
        }
    }

    public static void rpush(String key,String... value){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.rpush(key,value);
        }catch (Exception e){
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        }
    }

    /**
     * 获取key过期时间 -1表示永久 -2表示该key不存在
     * @param key
     * @return
     */
    public static long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JedisException(e.getMessage(),e);
        } finally {
            close(jedis);
        }
    }
}
