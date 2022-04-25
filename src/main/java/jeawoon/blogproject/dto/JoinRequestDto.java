package jeawoon.blogproject.dto;

import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
public class JoinRequestDto {

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotEmpty
    private String username;

    @NotBlank
    private String nickname;

    @Email
    private String email;
    private RoleType role;
    private String oauth;

    @Builder
    public JoinRequestDto(String loginId, String password, String username, String nickname, String email, RoleType role, String oauth){
        this.loginId=loginId;
        this.password=password;
        this.username=username;
        this.nickname=nickname;
        this.email=email;
        this.role=role;
        this.oauth=oauth;
    }

    public User toEntity(){
        User user = User.builder()
                .loginId(loginId)
                .password(password)
                .username(username)
                .nickname(nickname)
                .email(email)
                .role(role)
                .oauth(oauth)
                .build();
        return user;
    }
}
