package jeawoon.blogproject.dto.order;

import jeawoon.blogproject.entity.Address;
import jeawoon.blogproject.entity.OrderType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {

    private Long orderId;
    private String username;
    private LocalDateTime orderDate; //주문시간
    private Address address;
    private OrderType status;

    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count;      //주문 수량

    public OrderFlatDto(Long orderId, String username, LocalDateTime orderDate, OrderType status, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.username = username;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
