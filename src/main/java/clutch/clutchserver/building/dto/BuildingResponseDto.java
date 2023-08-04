package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    //지번 주소
    private String address;

    //동
    private String dong;

    //호
    private String ho;

    //근저당 설정 기준일
    //@Schema(defaultValue = "2023-07-23....")
    private LocalDate collateralDate;

    //건물 유형
    private Type type;

    @Builder
    public BuildingResponseDto(Long buildingId, String buildingName, String address, String dong, String ho, LocalDate collateralDate, Type type) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.collateralDate = collateralDate;
        this.type = type;
    }
}
