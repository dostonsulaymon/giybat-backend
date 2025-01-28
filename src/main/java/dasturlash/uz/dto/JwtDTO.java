package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String username;
    private String role;
    private String type;

    public JwtDTO(String username, String role, String type) {
        this.username = username;
        this.role = role;
        this.type = type;
    }
}
