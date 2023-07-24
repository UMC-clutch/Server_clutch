package clutch.clutchserver.global.common.jwt;

import clutch.clutchserver.global.common.ExpireTime;
import clutch.clutchserver.global.oauth.service.CustomUserDetailsService;
import clutch.clutchserver.token.entity.Token;
import clutch.clutchserver.token.repository.TokenRepository;
import clutch.clutchserver.user.dto.UserResponseDto;
import clutch.clutchserver.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.io.Decoders;

import java.security.Key;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";


    private final Key key;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${spring.token.secret}") String secretKey, TokenRepository tokenRepository, UserDetailsService userDetailsService) {
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // Kakao OAuth2 토큰 생성
    public UserResponseDto.TokenInfo generateKakaoToken(User kakaoInfo) {
        Claims claims = Jwts.claims().setSubject(kakaoInfo.getOauth2Id());

        // 추가적인 사용자 정보를 토큰에 추가할 경우
        claims.put("email", kakaoInfo.getEmail());
        claims.put("name", kakaoInfo.getName());

        long now = System.currentTimeMillis();
        long expirationTime = now + ExpireTime.ACCESS_TOKEN_EXPIRE_TIME;

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .claim("type", TYPE_ACCESS)
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .claim("type", TYPE_REFRESH)
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        Token token = Token.builder()
                .accessToken(accessToken)
                .accessTokenExpirationTime(ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshTokenExpirationTime(ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .user(kakaoInfo)
                .build();

        System.out.println(token.getAccessToken());

        tokenRepository.save(token);

        return UserResponseDto.TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpirationTime(ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(ExpireTime.REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    //name, authorities 를 가지고 AccessToken, RefreshToken 을 생성하는 메서드

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class); // 토큰 payload에서 email 값을 추출
    }

    // 토큰의 유효성을 검사하고 Authentication 객체를 리턴하는 메서드
    public Authentication getAuthentication(String token) {
        String email = extractEmail(token); // 토큰에서 email 추출
        UserDetails userDetails = userDetailsService.loadUserByUsername(email); // email을 사용하여 UserDetails 가져옴
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }



    //토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            // ???
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}