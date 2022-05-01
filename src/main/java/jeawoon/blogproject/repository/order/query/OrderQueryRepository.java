package jeawoon.blogproject.repository.order.query;

import jeawoon.blogproject.dto.order.OrderFlatDto;
import jeawoon.blogproject.dto.order.OrderListExcludeItemDto;
import jeawoon.blogproject.dto.order.Orderrr2Dto;
import jeawoon.blogproject.dto.order.OrderrrDto;
import jeawoon.blogproject.entity.Order;
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

    ///리포지토리에서 엔티티로조회후, DTO로 변환에 사용되는
    //쿼리좀 있긴하지만 간단버전
    public List<Order> findAllWithUserDelivery2() { //컬렉션 제외용
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.user u" +
                                " join fetch o.delivery d", Order.class)
                .getResultList();
    }
    public List<Order> findAllWithUserDelivery2(int offset, int limit) { //페이징용
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.user u" +
                                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findALLOrderList() { //컬렉션 포함, 페이징 불가
        //다기준으로 db서칭되므로, 중복많음 -> 앱에선 distinct처리따로 또 하지만, db에서 앱으로의 전송량이 많음
        ///-> 페이징 기능은 db결과기준이므로 중복에의한 오류생김 가능성 존재 + 중복데이터에의한 메모리 과부화 가능성 큼
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.user u" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }


    //////////////////////////////////////////////
    //컬렉션 제외용, 바로 DTO 직접 조회
    public List<OrderListExcludeItemDto> findAllWithUserDelivery() {
        return em.createQuery(
                        "select new jeawoon.blogproject.dto.order.OrderListExcludeItemDto(o.id, u.loginId, u.username, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.user u" +
                                " join o.delivery d", OrderListExcludeItemDto.class)
                .getResultList();
    }

    //컬렉션 포함, 바로 DTO 직접 조회
    public List<OrderrrDto> findOrderrr() {
        List<OrderrrDto> result = findOrderrr2(); //ToOne만 일단 조회, 쿼리 1번
        result.forEach(o -> { //(루프돌때마다 쿼리 나감 -> 최적화안된부분)
            List<Orderrr2Dto> orderItems = findOrderrr3(o.getOrderId()); //컬렉션 레이지 N쿼리
            o.setOrderItems(orderItems);
        });
        return result;
    }
    private List<OrderrrDto> findOrderrr2() {
        return em.createQuery(
                        "select new jeawoon.blogproject.dto.order.OrderrrDto(o.id, u.loginId, u.username, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.user u" +
                                " join o.delivery d", OrderrrDto.class)
                .getResultList();
    }
    private List<Orderrr2Dto> findOrderrr3(Long orderId) {
        return em.createQuery(
                        "select new jeawoon.blogproject.dto.order.Orderrr2Dto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = : orderId", Orderrr2Dto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


    ////컬렉션 포함 + 최적화, 바로 DTO 직접 조회
    public List<OrderrrDto> findAllByDto_optimization() {
        List<OrderrrDto> result = findOrderrr2(); //ToOne만 일단 조회, 쿼리 1번
        Map<Long, List<Orderrr2Dto>> orderItemMap = findOrderItemMap(toOrderIds(result)); //오더 아이디로 오더아이템 조회(in절 이용) = 쿼리 1번
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId()))); //오더아이템 레이지 => 메모리에서 매칭뿐.
        return result;
    }
    private List<Long> toOrderIds(List<OrderrrDto> result) { //오더의 아이디값 리스트로 반환
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }
    private Map<Long, List<Orderrr2Dto>> findOrderItemMap(List<Long> orderIds) {
        List<Orderrr2Dto> orderItems = em.createQuery( //오더 아이디값들 in절통해 오더아이템들 반환(쿼리1번인부분)
                        "select new jeawoon.blogproject.dto.order.Orderrr2Dto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", Orderrr2Dto.class) //in쿼리 이용
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream() //orderId기준으로 맵 반환
                .collect(Collectors.groupingBy(Orderrr2Dto::getOrderId));
    }


    //////컬렉션 포함 + 최적화 + 한방에 원쿼리, 바로 DTO 직접 조회
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                        "select new jeawoon.blogproject.dto.order.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d" +
                                " join o.orderItems oi" +
                                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }




}