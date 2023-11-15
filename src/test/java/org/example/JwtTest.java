package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {


    @Test
    public void generateJWT() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//添加过期时间
                .sign(Algorithm.HMAC256("csy"));//密钥

        System.out.println(token);
    }


    @Test
    public void parseJWT(){
        //定义字符串，模拟传过来的token
        //String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
        //        ".eyJleHAiOjE2OTk5ODA1NDksInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19" +
        //        ".Tu3ChSnNSwCAwS2X2q2MGjxZuTvUd86my_NS4gBp6EQ";
        //
        //JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("csy")).build();
        //DecodedJWT decodeJWT = jwtVerifier.verify(token);//验证toekn，生成一个解析后的jwt对象
        //Map<String, Claim> claims = decodeJWT.getClaims();
        //
        //System.out.println(claims.get("user"));
        //{"id":1,"username":"张三"}
        //如果篡改了头部或者载荷部分的数据，会验证失败
        //如果密钥改了，会验证失败
        //如果token过期，会验证失败
    }
}
