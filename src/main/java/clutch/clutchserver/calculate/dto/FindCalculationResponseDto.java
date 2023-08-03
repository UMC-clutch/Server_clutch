package clutch.clutchserver.calculate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class FindCalculateResponseDto {

    @Schema(description="계산 ID",example= "1")
    private Long id;

    @Schema(description="빌딩 ID",example="1")
    private Long buildingId;

    @Schema(description="주소 ID",example="1")
    private Long addressId;

    @Schema(description="주소",example="경기도 고양시 일산서구 일산3동")
    private String address;

    @Schema(description="동",example="505")
    private String dong;

    @Schema(description="호",example="606")
    private String ho;

    @Schema(description="근저당액",example="20000000")
    private int collateralMoney;

    @Schema(description="전세금",example="20000000")
    private int deposit;

    @Schema(description="위험여부",example= "true")
    private Boolean isDangerous;

    @Builder
<<<<<<< HEAD:src/main/java/clutch/clutchserver/calculate/dto/FindCalculationResponseDto.java
    public FindCalculationResponseDto(Long id, Long buildingId, Long addressId, String address, String dong, String ho, int collateralMoney, int deposit, Boolean isDangerous) {
=======
    public FindCalculateResponseDto(Long id, Long buildingId, Long addressId, String buildingName, String address, String dong, String ho, BigInteger price, BigInteger collateralMoney, BigInteger deposit, Boolean isDangerous) {
>>>>>>> 4fc5db61bc792814542780709d24c8478d4948d2:src/main/java/clutch/clutchserver/calculate/dto/FindCalculateResponseDto.java
        this.id = id;
        this.buildingId = buildingId;
        this.addressId = addressId;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.collateralMoney = collateralMoney;
        this.deposit = deposit;
        this.isDangerous = isDangerous;
    }
}
