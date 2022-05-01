package jeawoon.blogproject.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jeawoon.blogproject.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderItemDto {

    @JsonIgnore
    private Long orderId;   //주문번호
    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count;      //주문 수량

    public OrderItemDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
    public OrderItemDto(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getId();
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
