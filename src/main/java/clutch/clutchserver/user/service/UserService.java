package clutch.clutchserver.user.service;

import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.user.dto.UserRes;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {


    private final UserRepository userRepository;

    //유저 조회 (조회 기준 - 유저 email)
    public ResponseEntity<?> findUser(String useremail) {

        Optional<User> user = userRepository.findByEmail(useremail);
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");

        UserRes userRes = UserRes.builder()
                .id(user.get().getId())
                .email(user.get().getEmail())
                .name(user.get().getName())
                .phonenumber(user.get().getPhoneNumber())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(userRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
