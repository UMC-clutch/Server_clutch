package clutch.clutchserver.calculate.service;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import clutch.clutchserver.calculate.dto.CalculateResponseDto;
import clutch.clutchserver.calculate.dto.FindCalculateResponseDto;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalculateService {

    private final UserRepository userRepository;
    private final CalculateRepository calculateRepository;
    private final BuildingRepository buildingRepository;

    //사기 위험성 계산
    @Transactional      // read-only 해제
    public ResponseEntity<?> calculateRisk(String userEmail, CalculateRequestDto calculateReq) {

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        User user;
        DefaultAssert.isTrue(userOptional.isPresent(), "유저가 올바르지 않습니다.");
        user = userOptional.get();

        Optional<Building> buildingOptional = buildingRepository.findByBuildingId(calculateReq.getBuildingId());
        Building building;
        DefaultAssert.isTrue(buildingOptional.isPresent(), "올바르지 않은 빌딩입니다.");
        building = buildingOptional.get();

        // 근저당액 저장
        building.setCollateralMoney(calculateReq.getCollateral());

        Calculate calculate = Calculate.builder()
                .building(building)
                .deposit(calculateReq.getDeposit())
                .hasDanger(calculateReq.getIsDangerous())
                .user(user)
                .build();

        calculateRepository.save(calculate);

        CalculateResponseDto calculateRes = CalculateResponseDto.builder()
                .id(calculate.getId())
                .buildingId(calculate.getBuilding().getBuildingId())
                .deposit(calculate.getDeposit())
                .collateral(calculate.getBuilding().getCollateralMoney())
                .isDangerous(calculate.isHasDanger())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(calculateRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // 사기 위험성 계산 내역 조회(전체)
    public ResponseEntity<?> findCalculation(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        DefaultAssert.isTrue(userOptional.isPresent(), "유저가 올바르지 않습니다.");
        User user = userOptional.get();

        List<Calculate> calculateList = calculateRepository.findAllByUser(user);

        List<FindCalculateResponseDto> calculationResList = calculateList.stream().map(calculate -> {
            Building building = calculate.getBuilding();

            return FindCalculateResponseDto.builder()
                    .id(calculate.getId())
                    .buildingId(building.getBuildingId())
                    .buildingName(building.getBuildingName())
                    .address(building.getAddress())
                    .dong(building.getDong())
                    .ho(building.getHo())
                    .price(building.getPrice())
                    .collateralMoney(building.getCollateralMoney())
                    .deposit(calculate.getDeposit())
                    .isDangerous(calculate.isHasDanger())
                    .build();
        }).toList();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(calculationResList)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // 사기 위험성 계산 내역 조회(계산ID로 하나만)
    public ResponseEntity<?> findOneCalculation(String userEmail, Long id) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        DefaultAssert.isTrue(userOptional.isPresent(), "유저가 올바르지 않습니다.");

        Optional<Calculate> calculateOptional = calculateRepository.findById(id);
        DefaultAssert.isTrue(calculateOptional.isPresent(), "계산 ID가 올바르지 않습니다.");
        Calculate calculate = calculateOptional.get();

        Building building = calculate.getBuilding();

        FindCalculateResponseDto calculationRes = FindCalculateResponseDto.builder()
                .id(calculate.getId())
                .buildingId(building.getBuildingId())
                .buildingName(building.getBuildingName())
                .address(building.getAddress())
                .dong(building.getDong())
                .ho(building.getHo())
                .price(building.getPrice())
                .collateralMoney(building.getCollateralMoney())
                .deposit(calculate.getDeposit())
                .isDangerous(calculate.isHasDanger())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(calculationRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}