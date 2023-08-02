package clutch.clutchserver.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRes {

    private Long id;

    private String name;

    private String email;

    private String phonenumber;

    @Builder
    public UserRes(Long id, String name, String email, String phonenumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }
}