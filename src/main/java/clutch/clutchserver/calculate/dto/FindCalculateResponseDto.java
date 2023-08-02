package clutch.clutchserver.calculate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
public class FindCalculationResponseDto {

    @Schema(description="계산 ID",example= "1")
    private Long id;

    @Schema(description="빌딩 ID",example="1")
    private Long buildingId;

    @Schema(description="주소 ID",example="1")
    private Long addressId;

    @Schema(description="빌딩 이름",example="서서울삼성")
    private String buildingName;

    @Schema(description="주소",example="경기도 고양시 일산서구 일산3동")
    private String address;

    @Schema(description="동",example="505")
    private String dong;

    @Schema(description="호",example="606")
    private String ho;

    @Schema(description="시세",example="2875000000")
    private BigInteger price;

    @Schema(description="근저당액",example="20000000")
    private BigInteger collateralMoney;

    @Schema(description="전세금",example="20000000")
    private BigInteger deposit;

    @Schema(description="위험여부",example= "true")
    private Boolean isDangerous;

    @Builder
    public FindCalculationResponseDto(Long id, Long buildingId, Long addressId, String buildingName, String address, String dong, String ho, BigInteger price, BigInteger collateralMoney, BigInteger deposit, Boolean isDangerous) {
        this.id = id;
        this.buildingId = buildingId;
        this.addressId = addressId;
        this.buildingName = buildingName;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.price = price;
        this.collateralMoney = collateralMoney;
        this.deposit = deposit;
        this.isDangerous = isDangerous;
    }
}
