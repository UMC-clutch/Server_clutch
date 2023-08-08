package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BuildingResponseDto {

    @Schema(description="건물 ID",example= "1")
    private Long buildingId;

    @Schema(description="건물 이름",example= "서서울삼성")
    private String buildingName;

    @Schema(description="지번 주소",example= "서울특별시 마포구 아현동 692")
    private String address;

    @Schema(description="동",example= "101")
    private String dong;

    @Schema(description="호",example= "101")
    private String ho;

    @Schema(description="빌딩 ID",example= "2023-08-01T07:30:51.181Z")
    private LocalDate collateralDate;

    @Schema(description="빌딩 ID",example= "APARTMENT")
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
