package clutch.clutchserver.token.entity;
import clutch.clutchserver.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Setter
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grantType;

    @Column(length = 1000)
    private String accessToken;
    private Long accessTokenExpirationTime;

    @Column(length = 1000)
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
