package jeawoon.blogproject.dto.order;

import jeawoon.blogproject.entity.OrderType;
import lombok.Data;

@Data
public class OrderSearchDto {
    private String loginId; //회원 아이디
    private OrderType status; //주문 상태(ORDER, CANCEL)
}
