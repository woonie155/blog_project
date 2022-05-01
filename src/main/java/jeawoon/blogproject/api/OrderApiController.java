package jeawoon.blogproject.api;

import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.dto.order.*;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.repository.order.OrderRepository;
import jeawoon.blogproject.repository.order.query.OrderQueryRepository;
import jeawoon.blogproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    //오더의 연관관계
    // -XtoOne:  유저(N:1), 딜리버리(1:1),
    // -OneToMany: 오더아이템(1:N) ->컬렉션 (일대다는 페치조인시 다의 쿼리만큼 중복 -> JPA distinct이용)

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderService orderService;

    /////
    //주문 등록
    @PostMapping("/shop/order/saveProc")
    public ResponseDto<Integer> order_create(@RequestBody @Validated OrderSaveRequestDto dto){
        orderService.saveOrder(dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    //주문조회
    //1. 리포지토리에서 엔티티로조회후, DTO로 변환

    //컬렉션 제외 조회는, 어처피 페치조인으로만 쿼리1번이용해 가능 -> 중복없으므로 페이징 가능
    @GetMapping("/bbb") //컬렉션 포함 조회
    public List<OrderAllListDto> orders_listB1(){
        List<Order> orders = orderQueryRepository.findALLOrderList(); // 컬렉션까지 페치조인(2개이상은 불가) + distinct 쿼리1번 -> 페이징 불가
        List<OrderAllListDto> result = orders.stream()
                .map(o -> new OrderAllListDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/bbb_page")//컬렉션 포함 + 페이징처리
    public List<OrderAllListDto> orders_listB2(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                               @RequestParam(value = "limit", defaultValue = "100") int limit){
        return orderService.searchListAll(offset, limit);
    }


    /////////////////////////////////////////////////////////////////////
    //2. 리포지토리에서 DTO로 바로 조회
    @GetMapping("/aaa") //컬렉션 제외 조회
    public List<OrderListExcludeItemDto> orders_listA1(){
        return orderQueryRepository.findAllWithUserDelivery(); //페치조인 = 쿼리1번
    }
    @GetMapping("/aaa2") //컬렉션 포함 조회 + 단건 조회용
    public List<OrderrrDto> orders_listA2(){
        return orderQueryRepository.findOrderrr(); //페치조인 + 컬렉션은 별도 레이지처리메소드 = 1 + N
    }
    @GetMapping("/aaa3") //컬렉션 포함 조회 + 다건 조회용
    public List<OrderrrDto> orders_listA3(){
        return  orderQueryRepository.findAllByDto_optimization();  //1 + 1 쿼리
    }
    @GetMapping("/aaa4") //컬렉션 포함 조회 + 쿼리 단 한번 / 페이징 불가   //거의 비추
    public List<OrderrrDto> orders_listA4(){
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat(); //일단 다에 맞춰 중복해 가져오고 -> 페이징불가 = 1쿼리
        return flats.stream() //메모리에서 최적화
                .collect(groupingBy(o -> new OrderrrDto(o.getOrderId(), o.getUsername(), o.getOrderDate(), o.getStatus(), o.getAddress()),
                        mapping(o -> new Orderrr2Dto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderrrDto(e.getKey().getOrderId(), e.getKey().getUsername(), e.getKey().getOrderDate(), e.getKey().getStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList()); //정렬 소트는 별도지정해야함
    }
}
