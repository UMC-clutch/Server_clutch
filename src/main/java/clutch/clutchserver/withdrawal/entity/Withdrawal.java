package clutch.clutchserver.withdrawal.entity;

import clutch.clutchserver.global.common.enums.Reason;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Withdrawal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Reason reason;
}
