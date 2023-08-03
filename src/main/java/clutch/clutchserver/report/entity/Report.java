package clutch.clutchserver.report.entity;

import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.global.common.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "report")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고 상태 (완료, 심사 중)
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    private int repayment; // 최우선 변제금
    private boolean hasLowIncome; // 소액임차인 여부
    private boolean hasSubmittedContract; // 계약서 제출 여부
    private boolean hasResistance; // 대항력 여부
    private boolean hasRepayment; // 최우선 변제금 가능여부

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "report")
    private Contract contract;

    @Builder
    public Report(Long id, Contract contract, ReportStatus status, int repayment, boolean hasLowIncome, boolean hasSubmittedContract, boolean hasResistance, boolean hasRepayment) {
        this.id = id;
        this.status = status;
        this.contract = contract;
        this.repayment = repayment;
        this.hasLowIncome = hasLowIncome;
        this.hasSubmittedContract = hasSubmittedContract;
        this.hasResistance = hasResistance;
        this.hasRepayment = hasRepayment;
    }
}
