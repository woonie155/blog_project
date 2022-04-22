package jeawoon.blogproject.entity;

import jeawoon.blogproject.dto.JoinRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username; //아이디

    @Column(nullable = false, length = 100)
    private String password; //비밀번호

    @Column(length = 30)
    private String fullname; //이름

    @Column(length = 30, unique = true)
    private String nickname; //닉네임

    @Column(nullable = false, length = 50)
    private String email; //이메일

    @Enumerated(EnumType.STRING)
    private RoleType role; //권한

    private String oauth; //kakao, google, ...


    //
    @Builder
    public User(String username, String password, String nickname, String email, RoleType role, String oauth){
        this.username=username;
        this.password=password;
        this.nickname=nickname;
        this.email=email;
        this.role=role;
        this.oauth=oauth;
    }


}
