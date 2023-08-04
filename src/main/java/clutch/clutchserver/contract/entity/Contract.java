package clutch.clutchserver.contract.entity;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "contract")
public class Contract extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    private Boolean has_lived;

    private LocalDate transport_report_date;
    private LocalDate confirmation_date;
    private Boolean has_landlord_intervene;
    private Boolean has_applied_dividend;
    private BigInteger deposit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;




}

