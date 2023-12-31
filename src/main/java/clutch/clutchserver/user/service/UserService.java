package clutch.clutchserver.user.service;

import clutch.clutchserver.calculate.entity.Calculate;
import clutch.clutchserver.calculate.repository.CalculateRepository;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.contract.repository.ContractRepository;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.common.enums.Reason;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.image.entity.Image;
import clutch.clutchserver.image.repository.ImageRepository;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.report.repository.ReportRepository;
import clutch.clutchserver.token.entity.Token;
import clutch.clutchserver.token.repository.TokenRepository;
import clutch.clutchserver.user.dto.FindUserResponseDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import clutch.clutchserver.withdrawal.entity.Withdrawal;
import clutch.clutchserver.withdrawal.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final TokenRepository tokenRepository;
    private final CalculateRepository calculateRepository;
    private final ImageRepository imageRepository;
    private final ContractRepository contractRepository;
    private final ReportRepository reportRepository;

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
    public ResponseEntity<?> userDelete(String useremail, Reason reason) {

        // user 삭제
        Optional<User> user = userRepository.findByEmail(useremail);
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");
        userRepository.delete(user.get());

        // user와 연관된 토큰 삭제
        Optional<Token> token = tokenRepository.findByUserId(user.get().getId());
        DefaultAssert.isTrue(token.isPresent(), "유저가 올바르지 않습니다.");
        tokenRepository.delete(token.get());

        // user와 연관된 계산 내역 삭제
        List<Calculate> calculateList = calculateRepository.findAllByUser(user.get());
        calculateRepository.deleteAll(calculateList);


        // user 연관된 계약, 신고 삭제
        Optional<Contract> contract = Optional.ofNullable(contractRepository.findByUserId(user.get().getId()));
        if (contract.isPresent()) {
            contractRepository.delete(contract.get());
            Optional<Report> report = Optional.ofNullable(reportRepository.findByContractId(contract.get().getId()));
            report.ifPresent(reportRepository::delete);

            // user 연관된 이미지 삭제
            List<Image> images = imageRepository.findAllByUser(user.get());
            imageRepository.deleteAll(images);
        }

        // 탈퇴 사유 등록
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setReason(reason);
        withdrawalRepository.save(withdrawal);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(user.get())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updatePhone(User user, String phonenumber){
        // 사용자 정보를 업데이트합니다.
        try {
            System.out.println(user.getEmail());
            user.updatePhoneNum(phonenumber);
            userRepository.save(user);
            // 업데이트된 전화번호를 JSON 형태로 포함한 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("phonenumber", phonenumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리 (생략)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "저장 중 오류가 발생했습니다."));
        }
    }
}
