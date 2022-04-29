package jeawoon.blogproject;


import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.entity.*;
import jeawoon.blogproject.entity.item.Clothes;
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

            Delivery delivery = new Delivery();
            delivery.setAddress(user1.getAddress());
            Order order = Order.createOrder(user1, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
