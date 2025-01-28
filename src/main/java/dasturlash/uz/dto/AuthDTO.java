package dasturlash.uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @NotBlank(message = "username not blank")
    private String username;
    @NotBlank(message = "Password not blank")
    private String password;
}
