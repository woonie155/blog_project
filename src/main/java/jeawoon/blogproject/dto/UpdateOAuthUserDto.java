package jeawoon.blogproject.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class UpdateOAuthUserDto {

    private Long Id;

    private String LoginId;

    private String password;

    @NotBlank
    private String nickname;

}
