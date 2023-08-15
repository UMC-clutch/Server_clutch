package clutch.clutchserver.contract.dto;

import clutch.clutchserver.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
public class ContractRequestDto{

    @Schema(description = "거주 여부")
    private Boolean hasLived;


    @Schema(description = "운송 보고 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate transportReportDate;

    @Schema(description = "확인 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate confirmationDate;


    @Schema(description = "집주인 개입 여부")
    private Boolean hasLandlordIntervene;

    @Schema(description = "배당 신청 여부")
    private Boolean hasAppliedDividend;


    @Schema(description = "보증금", example = "5000000000")
    private BigInteger deposit;
}
