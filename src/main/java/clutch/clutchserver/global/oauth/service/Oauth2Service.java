package clutch.clutchserver.global.oauth.service;

import clutch.clutchserver.global.common.enums.AuthProvider;
import clutch.clutchserver.global.common.enums.Role;
import clutch.clutchserver.global.dto.AppleUserDto;
import clutch.clutchserver.global.dto.KakaoUserDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2Service {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private static final OkHttpClient client = new OkHttpClient();
    private static final String KAKAO_ID = "6368aa03461180e8130dac05a9218a4c";


    public User getAppleInfo(AppleUserDto appleUserDto) throws IOException, ParseException {

        String email = appleUserDto.getEmail();
        if(email==null){
            throw new IllegalArgumentException("Null Error");
        }




        // 이미 존재하는 이메일인지 확인
        Optional<User> existingUser = userRepository.findByEmail(email);
        System.out.println(existingUser);
        // 만약 이미 존재하는 사용자인 경우, 해당 사용자 객체를 반환
        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // User 생성
        User appleUser = User.builder()
                .oauth2Id(appleUserDto.getOauthId())
                .email(appleUserDto.getEmail())
                .phoneNumber(appleUserDto.getPhoneNumber())
                .name(appleUserDto.getName())
                .authProvider(AuthProvider.APPLE)
                .role(Role.ROLE_USER)
                .build();

//        // UserRepository를 사용하여 User 저장
        userRepository.save(appleUser);
        return appleUser;
    }

    public User getKInfo(KakaoUserDto kakaoUserDto) throws IOException, ParseException {

        String email = kakaoUserDto.getEmail();
        if(email==null){
            throw new IllegalArgumentException("Null error");
        }

        // 이미 존재하는 이메일인지 확인
        Optional<User> existingUser = userRepository.findByEmail(email);

        System.out.println(existingUser);
        // 만약 이미 존재하는 사용자인 경우, 해당 사용자 객체를 반환
        if (existingUser.isPresent()) {
            return existingUser.get();
        }


        // User 생성
        User kakaoUser = User.builder()
                .oauth2Id(kakaoUserDto.getOauthId())
                .email(kakaoUserDto.getEmail())
                .phoneNumber(kakaoUserDto.getPhoneNumber())
                .name(kakaoUserDto.getName())
                .authProvider(AuthProvider.KAKAO)
                .role(Role.ROLE_USER)
                .build();

//        // UserRepository를 사용하여 User 저장
        userRepository.save(kakaoUser);
        return kakaoUser;
    }





    public User getKakaoInfo(String accessToken) throws IOException, ParseException {

        // 예시로 주어진 액세스 토큰
        String accessTokenJson = accessToken;

        // "{"와 "}"를 제거하여 액세스 토큰 값만 추출
        String token = accessTokenJson.substring(accessTokenJson.indexOf(":\"") + 2, accessTokenJson.lastIndexOf("\""));

        // 추출한 액세스 토큰 출력
        System.out.println(token);

        String url = "https://kapi.kakao.com/v2/user/me";
        Request.Builder builder = new Request.Builder()
                .header("Authorization","Bearer "+token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .url(url);
        Request request = builder.build();
        System.out.println(request);

        Response responseHTML = client.newCall(request).execute();

        net.minidev.json.parser.JSONParser parser = new JSONParser();
        net.minidev.json.JSONObject info =(net.minidev.json.JSONObject) parser.parse(responseHTML.body().string());

        System.out.println(accessToken);


        // email 값과 profile의 nickname 값을 추출
        net.minidev.json.JSONObject kakaoAccount = (net.minidev.json.JSONObject) info.get("kakao_account");
        net.minidev.json.JSONObject profile = (JSONObject) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String email = (String) kakaoAccount.get("email");

        // 이미 존재하는 이메일인지 확인
        Optional<User> existingUser = userRepository.findByEmail(email);
        System.out.println(existingUser);
        // 만약 이미 존재하는 사용자인 경우, 해당 사용자 객체를 반환
        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // User 생성
        User user = User.builder()
                .email(email) // 이메일 값을 설정해야 함
                .name(nickname) // 이름 값을 설정해야 함
                .authProvider(AuthProvider.KAKAO)
                .role(Role.ROLE_USER)
                // authProvider, role 등 필요한 값들을 설정해야 함
                .build();

//        // UserRepository를 사용하여 User 저장
        userRepository.save(user);
        return user;
    }

    public JSONObject getKakaoAccessToken(String redirect_uri, String code) throws IOException, ParseException {
        String url = "https://kauth.kakao.com/oauth/token"
                + "?client_id=" + KAKAO_ID
                + "&client_secret=" + "oWp7qxtNnX9qHfO451kW0hsGGoZdIklz"
                + "&redirect_uri=" + redirect_uri
                + "&grant_type=authorization_code"
                + "&code=" + code;

        Request.Builder builder = new Request.Builder()
                .header("Content-type", "application/x-www-form-urlencoded")
                .url(url);

        JSONObject postObj = new JSONObject();
        RequestBody requestBody = RequestBody.create(postObj.toJSONString().getBytes());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseHTML.body().string());
        System.out.println(responseHTML);

        JSONObject response = new JSONObject();

        // "access_token"에 대한 null 체크
        Object accessTokenObj = obj.get("access_token");
        if (accessTokenObj != null) {
            response.put("access_token", accessTokenObj.toString());
        } else {
            // "access_token"이 null이면 오류 처리 또는 원하는 동작 수행
            // 예를 들어, 로그를 남기거나 예외를 던질 수 있습니다.
            // 여기에서는 빈 문자열("")로 처리했습니다.
            response.put("access_token", response);
        }

        return response;
    }

}