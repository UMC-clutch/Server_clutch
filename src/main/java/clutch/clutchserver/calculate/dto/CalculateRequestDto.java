package clutch.clutchserver.calculate.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CalculateRequestDto {

    private Long buildingId;        // 빌딩 ID

    private BigInteger collateral;  // 근저당액

    private BigInteger deposit;     // 전세금

    private Boolean isDangerous;    // 위험 여부

}