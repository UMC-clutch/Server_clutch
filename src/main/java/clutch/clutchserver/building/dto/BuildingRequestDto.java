package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BuildingRequestDto {

    @Schema(description = "건물명", example = "서서울삼성")
    private String buildingName;

    @Schema(description = "지번주소", example = "서울특별시 마포구 아현동 692")
    private String address;

    @Schema(description = "동", example = "101")
    private String dong;

    @Schema(description = "호", example = "101")
    private String ho;

    @Schema(description = "근저당 설정 기준일", example = "2023-08-01T07:30:51.181Z")
    private LocalDate collateralDate;

    @Schema(description = "접수 유형", example = "REPORT")
    private LogicType logicType;

    @Schema(description = "건물 유형", example = "APARTMENT")
    private Type type;

    @Schema(description = "평형 수", example = "64")
    private String area;

}
