package clutch.clutchserver.calculate.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CalculateRequestDto {

    private Long buildingId;

    private BigInteger deposit;

    private Boolean isDangerous;

}