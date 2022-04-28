package jeawoon.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String loginId;
    private String name;
    private String nickname;
    private String email;
}
