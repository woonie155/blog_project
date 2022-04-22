package jeawoon.blogproject.dto;

import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class JoinRequestDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private RoleType role;
    private String oauth;

    @Builder
    public JoinRequestDto(String username, String password, String nickname, String email, RoleType role, String oauth){
        this.username=username;
        this.password=password;
        this.nickname=nickname;
        this.email=email;
        this.role=role;
        this.oauth=oauth;
    }

    public User toEntity(){
        User user = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(role)
                .oauth(oauth)
                .build();
        return user;
    }
}
