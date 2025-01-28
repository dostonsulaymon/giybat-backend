package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileRoleDTO {
    @NotBlank(message = "Teh ID must not be empty")
    private Long id;
    @NotBlank(message = "The profileID must not be empty")
    private Long profileId;
    @NotBlank(message = "The role must not be empty")
    private ProfileRole role;
    @NotBlank(message = "The createDate must not be empty")
    private LocalDateTime createDate;
}
