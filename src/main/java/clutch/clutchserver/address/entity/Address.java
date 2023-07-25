package clutch.clutchserver.address.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    //주소 id
    @Id
    @GeneratedValue
    private Long addressId;

    //주소
    private String address;

    //동
    private String dong;

    //호
    private String ho;
}
