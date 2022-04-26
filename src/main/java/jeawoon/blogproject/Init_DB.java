package jeawoon.blogproject;


import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class Init_DB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        public void dbInit1() {

            String a=bCryptPasswordEncoder.encode("abc");
            JoinRequestDto j1 = JoinRequestDto.builder()
                    .loginId("user1")
                    .password(a)
                    .username("유저1")
                    .nickname("유저1닉네임")
                    .email("11111@nate.com")
                    .role(RoleType.USER)
                    .provider("")
                    .providerId("")
                    .build();
            JoinRequestDto j2 = JoinRequestDto.builder()
                    .loginId("user2")
                    .password(a)
                    .username("유저2")
                    .nickname("유저2닉네임")
                    .email("22222@nate.com")
                    .role(RoleType.USER)
                    .provider("")
                    .providerId("")
                    .build();

            User user1= j1.toEntity();
            User user2= j2.toEntity();

            em.persist(user1);
            em.persist(user2);
        }
    }
}
