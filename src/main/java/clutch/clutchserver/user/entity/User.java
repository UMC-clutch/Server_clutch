package clutch.clutchserver.user.entity;

import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.global.common.enums.AuthProvider;
import clutch.clutchserver.global.common.enums.Role;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Nullable
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;


    // 업데이트 메서드
    public void updateContract(Contract contract) {
            this.contract =contract;
    }
    public void updatePhoneNum(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
