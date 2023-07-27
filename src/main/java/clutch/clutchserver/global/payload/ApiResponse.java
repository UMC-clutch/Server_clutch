package clutch.clutchserver.global.payload;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ApiResponse {

    private boolean check;

    private Object information;

    public ApiResponse(){};

    @Builder
    public ApiResponse(boolean check, Object information) {
        this.check = check;
        this.information = information;
    }
}
