package jeawoon.blogproject.api;

import jeawoon.blogproject.dto.order.OrdersListDto;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.repository.order.OrderRepository;
import jeawoon.blogproject.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    //오더의 연관관계
    // -XtoOne:  유저(N:1), 딜리버리(1:1),
    // -OneToMany: 오더아이템(1:N) ->컬렉션

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    //모든 주문들 반환 -> 연관관계 페치조인처리: 유저와 딜리버리정보포함
    @GetMapping("/aaa")
    public List<OrdersListDto> orders_list(){ //리포지토리에서 DTO로 바로 조회
        return orderQueryRepository.findAllWithUserDelivery();
    }
//    @GetMapping
//    public List<OrdersListDto> orders_list2(){ //리포지토리에서 엔티티로조회후, DTO로 변환
//        List<Order> orders = orderQueryRepository.findAllWithUserDelivery2();
//        List<OrdersListDto> result = orders.stream()
//                .map(o -> new OrdersListDto(o))
//                .collect(toList());
//        return result;
//    }

    //컬렉션 관계
    @GetMapping("/bbb")
    public

}
