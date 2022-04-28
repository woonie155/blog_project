package jeawoon.blogproject.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UpdateUserDto {
    private Long Id;

    private String LoginId;

    @NotNull @Size(min=3)
    private String password;

    @NotBlank
    private String nickname;

    @Email
    private String email;
}
