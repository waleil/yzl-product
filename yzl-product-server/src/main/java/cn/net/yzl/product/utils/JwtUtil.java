package cn.net.yzl.product.utils;


import cn.net.yzl.api.entity.User;
import cn.net.yzl.oauth.config.JWTConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类，生成token，校验token
 */
@Component
public class JwtUtil {
    @Autowired
    private JWTConfig jwtConfig;

    /**
     * 生成token,设置token过期时间
     *
     * @param user
     * @return
     */
    public String createToken(User user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);

        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());

        //头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        JWTCreator.Builder builder = JWT.create();

        builder.withHeader(map)
                .withAudience()
                //用户基本信息
                .withClaim("userName", user.getUserName())
                .withClaim("flag", user.getFlag());
        builder.withClaim("loginType", user.getLoginType());
        if (StringUtils.isEmpty(user.getUserNo()))
            builder.withClaim("userId", user.getUserNo());

        if (user.getPhone() != null && !user.getPhone().isEmpty())
            builder.withClaim("phone", user.getPhone());


        String token = builder.withExpiresAt(expireDate)//设置过期时间
                .withIssuedAt(new Date())//签发时间
                .sign(algorithm);//加密
        return token;
    }

    /**
     * 校验token并解析token
     *
     * @param token
     * @return
     */
    public User verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(token);
        User user = new User();
        user.setUserName(jwt.getClaim("userName").asString());
        user.setUserNo(jwt.getClaim("userNo").asString());
        user.setFlag(jwt.getClaim("flag").asInt());
        user.setLoginType(jwt.getClaim("loginType").asInt());
        user.setPhone(jwt.getClaim("phone").asString());
        return user;
    }

    public User decodeToken(String token){
        User user=null;
        try {
            DecodedJWT jwt = JWT.decode(token);
            user = new User();
            if (!jwt.getClaim("userName").isNull()){
                user.setUserName(jwt.getClaim("userName").asString());
            }
            if (!jwt.getClaim("userNo").isNull()){
                user.setUserNo(jwt.getClaim("userNo").asString());
            }
            if (!jwt.getClaim("phone").isNull()){
                user.setPhone(jwt.getClaim("phone").asString());
            }
            if (!jwt.getClaim("flag").isNull()){
                user.setFlag(jwt.getClaim("flag").asInt());
            }
            if (!jwt.getClaim("loginType").isNull()){
                user.setLoginType(jwt.getClaim("loginType").asInt());
            }
        } catch (Exception e) {
            return null;
        }

        return user;
    }

}
