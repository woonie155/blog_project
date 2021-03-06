package jeawoon.blogproject;


import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.entity.*;
import jeawoon.blogproject.entity.item.Clothes;
import jeawoon.blogproject.repository.UserRepository;
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
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserRepository userRepository;

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
            user1.setAddress(new Address("서울 서대문구 북가좌동", "311-1", "A동 111호"));
            User user2= j2.toEntity();
            user2.setAddress(new Address("서울 서대문구 남가좌동", "5115-1", "B동 111호"));


            em.persist(user1);
            em.persist(user2);

            Clothes clothes1 = new Clothes();
            clothes1.setName("둘리티셔츠");
            clothes1.setPrice(10000);
            clothes1.setStockQuantity(50);
            clothes1.setBrand("투니버스");
            em.persist(clothes1);

            Clothes clothes2= new Clothes();
            clothes2.setName("민첩티셔츠");
            clothes2.setPrice(8000);
            clothes2.setStockQuantity(99);
            clothes2.setBrand("투니버스");
            em.persist(clothes2);

            OrderItem orderItem1 = OrderItem.createOrderItem(clothes1,10000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(clothes2,8000, 1);
            OrderItem orderItem3 = OrderItem.createOrderItem(clothes2,8000, 3);

            Delivery delivery1 = new Delivery();
            delivery1.setAddress(user1.getAddress());
            Delivery delivery2 = new Delivery();
            delivery2.setAddress(user1.getAddress());

            Order order1= Order.createOrder(user1, delivery1, orderItem1, orderItem2);
            Order order2= Order.createOrder(user2, delivery2, orderItem3);
            em.persist(order1);
            em.persist(order2);

        }
        public void dbInit2() {
            User user = userRepository.findByLoginId("user1").get();
            Board board = new Board(user, "안녕하세요1!!", "아무내용입니다1.");
            Board board2 = new Board(user, "반갑습니다2!!", "아무내용입니다2.");
            Board board3 = new Board(user, "ABCDE3!!", "아무내용입니다3.");
            Board board4 = new Board(user, "제목입니다4!!", "아무내용입니다4.");
            em.persist(board);
            em.persist(board2);em.persist(board3);em.persist(board4);
        }
    }
}
