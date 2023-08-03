package clutch.clutchserver.user.service;

import clutch.clutchserver.calculate.entity.Calculate;
import clutch.clutchserver.calculate.repository.CalculateRepository;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.common.enums.Reason;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.token.entity.Token;
import clutch.clutchserver.token.repository.TokenRepository;
import clutch.clutchserver.user.dto.FindUserResponseDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import clutch.clutchserver.withdrawal.entity.Withdrawal;
import clutch.clutchserver.withdrawal.repository.WithdrawalRepository;
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
    private final WithdrawalRepository withdrawalRepository;
    private final TokenRepository tokenRepository;
    private final CalculateRepository calculateRepository;

    //유저 조회 (조회 기준 - 유저 email)
    public ResponseEntity<?> findUser(String useremail) {

        Optional<User> user = userRepository.findByEmail(useremail);
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");

        FindUserResponseDto userRes = FindUserResponseDto.builder()
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

    // 유저 탈퇴
    @Transactional
    public void userDelete(String useremail, Reason reason) {

        // user 삭제
        Optional<User> user = userRepository.findByEmail(useremail);
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");
        userRepository.delete(user.get());

        // user와 연관된 토큰 삭제
        Optional<Token> token = tokenRepository.findByUserId(user.get().getId());
        DefaultAssert.isTrue(token.isPresent(), "유저가 올바르지 않습니다.");
        tokenRepository.delete(token.get());

        // user와 연관된 계산 내역 삭제
        Calculate findCalculate = calculateRepository.findByUserId(user.get().getId());
        calculateRepository.delete(findCalculate);

        // 탈퇴 사유 등록
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setReason(reason);
        withdrawalRepository.save(withdrawal);
    }

}
