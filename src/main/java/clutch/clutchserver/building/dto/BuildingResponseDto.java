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
    //@Schema(defaultValue = "1")
    private Long buildingId;

    //건물 이름
    //@Schema(defaultValue = "래미안")
    private String buildingName;

    //근저당 설정 기준일
    //@Schema(defaultValue = "2023-07-23....")
    private LocalDateTime collateralDate;

    //주소
    private Address address;

    //건물 유형
    private Type type;

    @Builder
    public BuildingResponseDto(Long buildingId, String buildingName, LocalDateTime collateralDate, Address address, Type type) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.collateralDate = collateralDate;
        this.address = address;
        this.type = type;
    }
}
