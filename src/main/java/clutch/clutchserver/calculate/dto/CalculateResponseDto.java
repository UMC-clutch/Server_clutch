package clutch.clutchserver.calculate.dto;

import lombok.Builder;

public class CalculateResponseDto {

    private Long id;

    private Long buildingId;

    private int deposit;

    private Boolean isDangerous;

    @Builder
    public CalculateResponseDto(Long id, Long buildingId, int deposit, Boolean isDangerous) {
        this.id = id;
        this.buildingId = buildingId;
        this.deposit = deposit;
        this.isDangerous = isDangerous;
    }
}