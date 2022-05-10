package jeawoon.blogproject.repository.order;

import jeawoon.blogproject.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o join fetch o.user u join fetch o.delivery d", countQuery = "select count(o) from Order o")
    Page<Order> findAllWithUserDeliveryBy(Pageable pageable);


    @EntityGraph(attributePaths = {"orderItems"})
    @Query(value = "select o from Order o join fetch o.user u join fetch o.delivery d", countQuery = "select count(o) from Order o")
    Page<Order> findAllWithUserDeliveryBy2(Pageable pageable);
}
