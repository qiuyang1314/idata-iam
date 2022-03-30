package com.zyaud.idata.iam.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author Harry_shine
 */
@Data
@Component
public class JwtUtil {

    private static String key;

    @Value("${jwt.config.key:zyaud}")
    public void setKey(String key) {
        JwtUtil.key = key;
    }

    private static long test;

    @Value("${jwt.config.test:3600000}")
    public void setTest(long test) {
        JwtUtil.test = test;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public static String createJWT(String id, String subject, Map<String, Object> map) {
        long nowMillis = System.currentTimeMillis();
        long exp = nowMillis + test;
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        builder.setClaims(map);
        if (test > 0) {
            builder.setExpiration(new Date(exp));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public static Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
