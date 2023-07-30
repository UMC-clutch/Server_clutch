package clutch.clutchserver.calculate.controller;

import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import clutch.clutchserver.calculate.service.CalculateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    // 사기 위험성 계산
    @PostMapping ("/api/calculate")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> calculateRisk(
            @Valid @RequestBody CalculateRequestDto calculateReq
    ) throws JsonProcessingException {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return calculateService.calculateRisk(useremail, calculateReq);
    }

}