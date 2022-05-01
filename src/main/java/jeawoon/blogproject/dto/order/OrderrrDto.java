package jeawoon.blogproject.dto.order;

import jeawoon.blogproject.entity.Address;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.entity.OrderType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(of = "orderId") //그룹핑 기준
public class OrderrrDto {

    private Long orderId;
    private String username;
    private LocalDateTime orderDate; //주문시간
    private OrderType status;
    private Address address; //밸류값은 ㄱㅊ 걍써도댐
    private List<Orderrr2Dto> orderItems; //컬렉션 부분 엔티티 -> 이또한 DTO로 설정

    public OrderrrDto(Long orderId, String username, LocalDateTime orderDate, OrderType status, Address address){
        this.orderId=orderId;
        this.username=username;
        this.orderDate=orderDate;
        this.status=status;
        this.address=address;
    }
    public OrderrrDto(Long orderId, String username, LocalDateTime orderDate, OrderType status, Address address, List<Orderrr2Dto> orderItems){
        this.orderId=orderId;
        this.username=username;
        this.orderDate=orderDate;
        this.status=status;
        this.address=address;
        this.orderItems = orderItems;
    }

}
