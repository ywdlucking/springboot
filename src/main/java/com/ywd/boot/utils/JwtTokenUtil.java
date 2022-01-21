package com.ywd.boot.utils;

import com.ywd.boot.exception.BaseErrorEnum;
import com.ywd.boot.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtTokenUtil {
    private static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 解析jwt
     * @param jsonWebToken
     * @param pubKey
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String pubKey) {
        try {
            PublicKey publicKey = generatePublicKey(pubKey);
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException  eje) {
            log.error("===== Token过期 =====", eje);
            throw new BizException(BaseErrorEnum.EXPIRED);
        } catch (Exception e){
            log.error("===== token解析异常 =====", e);
            log.error("===== 异常token {} =====", jsonWebToken);
            throw new BizException(BaseErrorEnum.UNAUTHORIZED);
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUsername(String token, String base64Security){
        return parseJWT(token, base64Security).getSubject();
    }

    /**
     * 是否已过期
     * @param token
     * @param base64Security
     * @return
     */
    public static boolean isExpiration(String token, String base64Security) {
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }

    public static PublicKey generatePublicKey(String src) throws Exception{
        byte[] publicBytes = Base64.getDecoder().decode(src);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiMCIsInVzZXJJZCI6IjU2NTI3MTYyLWVkZTktNDQ1Mi1hZDU1LTE0NGI1MTQ5MDdiZiIsInN1YiI6IlNISUUiLCJpc3MiOiJzOTQyODYwNjc5NDY1OTMwNzUyIiwiaWF0IjoxNjI4NzI4OTM1LCJhdWQiOiJzaGllIn0.CdXCuyup01bnjG_7N3mfgttc3ikm2psFGpz5NEAwO4eh2tXaMScWubP-TR-EAmkNHXAIxr5TE2fOCarkKR8Pv_VTKOGYsef2ESQejKLYTgG9Ph2hT7EZ5RJZQPn4k72WSsUNga1Uj5DKQ3licTiYinci24A6iVMtuLMm98txNbk";
        String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW+se9l841Ypx6LG6bgxh44Kjcmd5N8eEtHiiA3eCJU990fF1hQtt+asznYKhzsvqFEEfRgmWXy7tBSNtQJDSxtU8BTlD207dGYZJG6QDNRKPWwSstFMp6E0thHNxIs76jHsPiNXGTgUUVhFMDImneTM04wK1KZEr6UOLfDrs5lQIDAQAB";
        Claims claims = parseJWT(token, pubkey);
        System.out.println(claims.getSubject());
    }
}
