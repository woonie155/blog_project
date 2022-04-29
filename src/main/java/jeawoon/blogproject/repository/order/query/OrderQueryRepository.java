package jeawoon.blogproject.repository.order.query;

import jeawoon.blogproject.dto.order.OrdersListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    //주문목록 조회시 DTO이용해 select최적화 + LAZY인 유저와배송정보 페치조인
    public List<OrdersListDto> findAllWithUserDelivery() {
        return em.createQuery(
                        "select new jeawoon.blogproject.dto.order.OrdersListDto(o.id, u.loginId, u.username, o.orderDate, o.status, d.address)" +
                                "from Order o" +
                                " join o.user u" +
                                " join o.delivery d", OrdersListDto.class)
                .getResultList();
    }
    //쿼리좀 있긴하지만 간단버전
//    public List<Order> findAllWithUserDelivery2() {
//        return em.createQuery(
//                        "select o from Order o" +
//                                " join fetch o.user u" +
//                                " join fetch o.delivery d", Order.class)
//                .getResultList();
//    }

}