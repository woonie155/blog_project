package jeawoon.blogproject.repository.order;

import jeawoon.blogproject.dto.order.OrderSearchDto;
import jeawoon.blogproject.dto.order.OrdersListDto;
import jeawoon.blogproject.entity.Address;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.entity.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository{

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearchDto dto) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (dto.getStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 아이디로 검색
        if (StringUtils.hasText(dto.getLoginId())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (dto.getStatus() != null) {
            query = query.setParameter("status", dto.getStatus());
        }
        if (StringUtils.hasText(dto.getLoginId())) {
            query = query.setParameter("name", dto.getLoginId());
        }

        return query.getResultList();
    }



}
