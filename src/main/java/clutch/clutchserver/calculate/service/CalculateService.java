package clutch.clutchserver.calculate.service;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import clutch.clutchserver.calculate.dto.CalculateResponseDto;
import clutch.clutchserver.calculate.entity.Calculate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import clutch.clutchserver.calculate.repository.CalculateRepository;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalculateService {

    private final UserRepository userRepository;
    private final CalculateRepository calculateRepository;
    private final BuildingRepository buildingRepository;

    //사기 위험성 계산
    public ResponseEntity<?> calculateRisk(String useremail, CalculateRequestDto calculateReq) {

        Optional<User> userOptional = userRepository.findByEmail(useremail);
        User user;
        DefaultAssert.isTrue(userOptional.isPresent(), "유저가 올바르지 않습니다.");
        user = userOptional.get();

        Optional<Building> buildingOptional = buildingRepository.findByBuildingId(calculateReq.getBuildingId());
        Building building;
        DefaultAssert.isTrue(buildingOptional.isPresent(), "올바르지 않은 빌딩입니다.");
        building = buildingOptional.get();

        Calculate calculate = Calculate.builder()
                .building(building)
                .deposit(calculateReq.getDeposit())
                .hasDanger(calculateReq.getIsDangerous())
                .build();

        calculateRepository.save(calculate);

        CalculateResponseDto calculateRes = CalculateResponseDto.builder()
                .id(calculate.getId())
                .buildingId(calculate.getBuilding().getBuildingId())
                .deposit(calculate.getDeposit())
                .isDangerous(calculate.isHasDanger())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(calculateRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}