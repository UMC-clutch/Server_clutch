package clutch.clutchserver.user.entity;

import clutch.clutchserver.user.enums.OAuthProvider;
import clutch.clutchserver.user.enums.Roles;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;



@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;

    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private OAuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Roles role;


    @Nullable
    private String phonenumber;


}