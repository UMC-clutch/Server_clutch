package clutch.clutchserver.building.dto;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.global.common.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BuildingResponseDto {

    //건물 id
    private Long buildingId;

    //건물 이름
    private String buildingName;

    //근저당 설정 기준일
    private LocalDateTime collateralDate;

    //주소
    private String address;

    //건물 유형
    private Type type;

    @Builder
    public BuildingResponseDto(Long buildingId, String buildingName, LocalDateTime collateralDate, String address, Type type) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.collateralDate = collateralDate;
        this.address = address;
        this.type = type;
    }
}
