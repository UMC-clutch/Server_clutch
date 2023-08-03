package clutch.clutchserver.calculate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CalculateRequest")
public class CalculateRequestDto {

<<<<<<< HEAD
    private Long buildingId;

    private int deposit;

    private Boolean isDangerous;
=======
    @Schema(description = "빌딩 ID", example = "1")
    private Long buildingId;

    @Schema(description = "근저당액", example = "2000000000")
    private BigInteger collateral;  // 근저당액

    @Schema(description = "전세금", example = "2000000000")
    private BigInteger deposit;     // 전세금

    @Schema(description = "위험 여부", example = "true")
    private Boolean isDangerous;    // 위험 여부
>>>>>>> 4fc5db61bc792814542780709d24c8478d4948d2

}