package jeawoon.blogproject;


import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import lombok.RequiredArgsConstructor;
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

        public void dbInit1() {
            User user1 = createUser("ssar", "1234","ssar@naver.com", RoleType.USER);
            User user2 = createUser("love", "1234","love@naver.com", RoleType.USER);
            User user3 = createUser("cos", "1234","cos@naver.com", RoleType.USER);

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

        }

        private User createUser(String name, String pw, String email, RoleType role){
            User user = new User();
            user.setUsername(name);
            user.setPassword(pw);
            user.setEmail(email);
            user.setRole(role);
            return user;
        }
    }
}
