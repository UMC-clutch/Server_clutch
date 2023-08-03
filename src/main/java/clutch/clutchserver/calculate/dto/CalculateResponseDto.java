package clutch.clutchserver.calculate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
public class CalculateResponseDto {

    @Schema(description="계산 ID",example= "1")
    private Long id;

    @Schema(description="빌딩 ID",example= "1")
    private Long buildingId;

    @Schema(description="근저당액",example= "5000000000")
    private BigInteger collateral;

    @Schema(description="전세금",example= "5000000000")
    private BigInteger deposit;

    @Schema(description="위험 여부",example= "1")
    private Boolean isDangerous;

    @Builder
    public CalculateResponseDto(Long id, Long buildingId, BigInteger deposit, BigInteger collateral, Boolean isDangerous) {
        this.id = id;
        this.buildingId = buildingId;
        this.collateral = collateral;
        this.deposit = deposit;
        this.isDangerous = isDangerous;
    }
}