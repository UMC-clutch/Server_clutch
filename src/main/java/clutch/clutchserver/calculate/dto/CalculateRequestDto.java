package clutch.clutchserver.calculate.dto;

import lombok.Data;

@Data
public class CalculateRequestDto {

    private Long buildingId;

    private int deposit;

    private Boolean isDangerous;

}