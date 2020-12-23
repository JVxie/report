package com.jvxie.report.utils;

import com.jvxie.report.exception.UserNeedLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
    private static final String TOKEN_PREFIX = "Token_%s";
    private static final int TOKEN_EXPIRE = 604800;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Map<String, String> getTokenMap(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new UserNeedLoginException();
        }
        String[] tokenArray = authorization.split(" ");
        if (tokenArray.length < 2) {
            throw new UserNeedLoginException();
        }
        String tokenFromRedis = redisTemplate.opsForValue().get(
                String.format(TOKEN_PREFIX, tokenArray[1])
        );
        if (StringUtils.isEmpty(tokenFromRedis)) {
            throw new UserNeedLoginException();
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", tokenArray[1]);
        resultMap.put("tokenFromRedis", tokenFromRedis);
        return resultMap;
    }

    public Boolean checkToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return false;
        }
        String[] tokenArray = authorization.split(" ");
        if (tokenArray.length < 2) {
            throw new UserNeedLoginException();
        }
        String tokenFromRedis = redisTemplate.opsForValue().get(
                String.format(TOKEN_PREFIX, tokenArray[1])
        );
        if (StringUtils.isEmpty(tokenFromRedis)) {
            return false;
        }
        return true;
    }

    public void setToken(String token, String value) {
        redisTemplate.opsForValue().set(
                String.format(TOKEN_PREFIX, token),
                value,
                TOKEN_EXPIRE,
                TimeUnit.SECONDS
        );
    }

    public void removeToken(String token) {
        redisTemplate.opsForValue().getOperations().delete(
                String.format(TOKEN_PREFIX, token)
        );
    }
}
