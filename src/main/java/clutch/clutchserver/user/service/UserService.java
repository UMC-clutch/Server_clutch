package clutch.clutchserver.user.service;

import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.payload.ApiResponse;
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

    //유저 조회 (조회 기준 - 유저 id)
    public ResponseEntity<?> findUser(String useremail) {

        Optional<User> user = userRepository.findByEmail(useremail);
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");


        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(user)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
