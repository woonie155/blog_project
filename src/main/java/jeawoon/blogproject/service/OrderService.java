package jeawoon.blogproject.service;

import jeawoon.blogproject.dto.order.OrderAllListDto;
import jeawoon.blogproject.dto.order.OrderItemDto;
import jeawoon.blogproject.dto.order.OrderListExcludeItemDto;
import jeawoon.blogproject.dto.order.OrderSaveRequestDto;
import jeawoon.blogproject.entity.*;
import jeawoon.blogproject.entity.item.Item;
import jeawoon.blogproject.repository.ItemRepository;
import jeawoon.blogproject.repository.UserRepository;
import jeawoon.blogproject.repository.order.OrderRepository;
import jeawoon.blogproject.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional //주문저장
    public void saveOrder(OrderSaveRequestDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("일치하는 회원 없음 에러");
        });
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> {
            return new IllegalArgumentException("일치하는 아이템 없음 에러");
        });

        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryType.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), dto.getCount());
        Order order = Order.createOrder(user, delivery, orderItem);
        orderRepository.save(order);
    }

    //모든주문목록 반환
    public List<OrderAllListDto> searchListAll(int offset, int limit) {
        List<Order> orders = orderQueryRepository.findAllWithUserDelivery2(offset, limit); //오더에 유저, 딜리버리만 일단 쿼리 //배치사이즈로 컬렉션은 in쿼리로 포함
        List<OrderAllListDto> result = orders.stream()//컬렉션은 분리-> 지연로딩 처리(배치사이즈이용해 최적화)
                .map(o-> new OrderAllListDto(o))
                .collect(Collectors.toList());
        return result;
    }
    //주문아이템컬렉션 제외하고 일단 설계
    public Page<OrderListExcludeItemDto> searchPageAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAllWithUserDeliveryBy(pageable);
        return page.map(o -> new OrderListExcludeItemDto(o.getId(), o.getUser().getLoginId(), o.getUser().getUsername(), o.getOrderDate(), o.getStatus(), o.getUser().getAddress()));
    }
    //주문아이템컬렉션 포함 페이지 설계
    public Page<OrderAllListDto> searchPageAll2(Pageable pageable) {
        Page<Order> page = orderRepository.findAllWithUserDeliveryBy2(pageable);
        List<OrderAllListDto> result = page.stream()
                .map(o -> new OrderAllListDto(o)).collect(Collectors.toList());
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }
}