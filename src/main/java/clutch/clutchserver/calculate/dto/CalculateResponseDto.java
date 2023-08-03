package clutch.clutchserver.calculate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigInteger;

public class CalculateResponseDto {

    @Schema(description="계산 ID",example= "1")
    private Long id;

    @Schema(description="빌딩 ID",example= "1")
    private Long buildingId;

    @Schema(description="전세금",example= "1")
    private BigInteger deposit;

    @Schema(description="위험 여부",example= "1")
    private Boolean isDangerous;

    @Builder
    public CalculateResponseDto(Long id, Long buildingId, BigInteger deposit, Boolean isDangerous) {
        this.id = id;
        this.buildingId = buildingId;
        this.deposit = deposit;
        this.isDangerous = isDangerous;
    }
}