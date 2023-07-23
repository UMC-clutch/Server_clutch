package clutch.clutchserver.global.oauth.service;

import clutch.clutchserver.global.jwt.JwtTokenProvider;
import clutch.clutchserver.token.repository.TokenRepository;
import clutch.clutchserver.user.dto.UserResponseDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public UserResponseDto.TokenInfo login(User kakaoInfo) {
        User user = userRepository.findByEmail(kakaoInfo.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UsernameNotFoundException.class.getName()));
        UserResponseDto.TokenInfo tokenDto = jwtProvider.generateKakaoToken(kakaoInfo);
        //String refreshToken = tokenDto.getRefreshToken();



        log.error(user.getName()+" 로그인!");
        return tokenDto;
    }




}