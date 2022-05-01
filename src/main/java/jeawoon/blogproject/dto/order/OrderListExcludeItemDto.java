package jeawoon.blogproject.dto.order;

import jeawoon.blogproject.entity.Address;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.entity.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderListExcludeItemDto {

    private Long orderId;
    private String loginId;
    private String username;
    private LocalDateTime orderDate;
    private OrderType status;
    private Address address;

    //JPA 반환 가능케
    public OrderListExcludeItemDto(Long orderId, String loginId, String username, LocalDateTime orderDate, OrderType status, Address address){
        this.orderId = orderId;
        this.loginId = loginId;
        this.username = username;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
    }
}
