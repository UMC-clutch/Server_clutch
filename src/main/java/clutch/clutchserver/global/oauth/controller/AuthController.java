package clutch.clutchserver.global.oauth.controller;

import clutch.clutchserver.global.dto.AppleUserDto;
import clutch.clutchserver.global.dto.KakaoUserDto;
import clutch.clutchserver.global.jwt.JwtTokenProvider;
import clutch.clutchserver.global.oauth.service.Oauth2Service;
import clutch.clutchserver.global.oauth.service.SecurityService;
import clutch.clutchserver.user.dto.UserResponseDto;
import clutch.clutchserver.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth", description = "로그인과 관련된 API")
public class AuthController {
    private static final String KAKAO_ID = "6368aa03461180e8130dac05a9218a4c";
    private final Oauth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityService securityService;

    @PostMapping("/api/login/apple")
    @Operation(summary = "Apple Login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description ="{user:testuser,userId:sdjahkfhkjdhsjafhksd,phonenumber:010-1234-1234,email:email@email.com}")
    public ResponseEntity<clutch.clutchserver.global.payload.ApiResponse> getAppleToken(@Valid @RequestBody AppleUserDto appleUserDto) throws Exception{
        try {
            User appleInfo = oAuth2Service.getAppleInfo(appleUserDto);
            UserResponseDto.TokenInfo tokenDTO = securityService.appleLogin(appleInfo);
            clutch.clutchserver.global.payload.ApiResponse apiResponse = clutch.clutchserver.global.payload.ApiResponse.builder()
                    .check(true)
                    .information(tokenDTO)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            // 예외 상황에 대한 처리
            clutch.clutchserver.global.payload.ApiResponse errorResponse = clutch.clutchserver.global.payload.ApiResponse.builder()
                    .check(false)
                    .information(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PostMapping("/api/login/kakao")
    @Operation(summary = "Kakao Login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description ="{user:testuser,userId:sdjahkfhkjdhsjafhksd,phonenumber:010-1234-1234,email:email@email.com}")
    public ResponseEntity<clutch.clutchserver.global.payload.ApiResponse> getKToken(@Valid @RequestBody KakaoUserDto kakaoUserDto) throws Exception{
        try {
            User kInfo = oAuth2Service.getKInfo(kakaoUserDto);
            UserResponseDto.TokenInfo tokenDTO = securityService.kakaoLogin(kInfo);
            clutch.clutchserver.global.payload.ApiResponse apiResponse = clutch.clutchserver.global.payload.ApiResponse.builder()
                    .check(true)
                    .information(tokenDTO)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            // 예외 상황에 대한 처리
            clutch.clutchserver.global.payload.ApiResponse errorResponse = clutch.clutchserver.global.payload.ApiResponse.builder()
                    .check(false)
                    .information(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping(value = "/api/auth/token/kakao")
    @Operation(summary = "Get OAuth Token with Kakao Token")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the OAuth token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.TokenInfo.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "{access_token: \"oUsN9A5fj572aZlFI0G3Fasi-httNnCvvUvcD-PaCiolEAAAAYl7CB7_\"})")
    public ResponseEntity<UserResponseDto.TokenInfo> getOauthToken(@RequestBody String token) throws Exception{
        User kakaoInfo = oAuth2Service.getKakaoInfo(token);
        UserResponseDto.TokenInfo tokenDTO = securityService.login(kakaoInfo);
        return ResponseEntity.ok(tokenDTO);
    }

    //이부분은 개발할때 accesstoken얻기 위한 로직들!
    @Operation(description = "스프링용 카카오로그인 실행(code받아오기)")
    @GetMapping("/api/auth/test1")
    public String kakaoLogin() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=" + KAKAO_ID +
                "&redirect_uri=http://localhost:8080/api/auth/token/kakao&response_type=code";
    }

    @Operation(description= "스프링용 카카오 액세스토큰 추출로직")
    @GetMapping(value = "/api/auth/token/kakao")
    public JSONObject oauthKakao(@RequestParam(value = "code", required = false) String code) throws Exception {
        log.warn("인가코드 = {}",code);
        return oAuth2Service.getKakaoAccessToken("https://www.clutch.p-e.kr/api/auth/token/kakao", code);
    }




    @GetMapping(value = "/api/v1/test")
    @SecurityRequirement(name = "access-token")
    public String test(HttpServletRequest request) throws Exception{
        // 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 인증된 사용자의 정보를 기반으로 추가적인 작업 수행 가능
        // (예: 사용자 정보를 활용한 비즈니스 로직 등)

        return "Hello " + username + "! Success";
    }
}