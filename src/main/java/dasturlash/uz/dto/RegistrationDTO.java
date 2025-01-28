package dasturlash.uz.dto;


import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "Password required")
    private String password;

}
