package clutch.clutchserver.report.entity;

import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.global.common.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report")
@Getter @Setter
public class Report extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

// 연관관계 매핑
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "contract_id")
//    private Contract contract;

    // 신고 상태 (완료, 심사 중)
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    private int repayment; // 최우선 변제금
    private boolean hasLowIncome; // 소액임차인 여부
    private boolean hasSubmittedContract; // 계약서 제출 여부
    private boolean hasResistance; // 대항력 여부
    private boolean hasRepayment; // 최우선 변제금 가능여부
}
