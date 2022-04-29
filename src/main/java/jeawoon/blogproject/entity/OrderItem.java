package jeawoon.blogproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jeawoon.blogproject.entity.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 넣을 당시, 가격
    private int count; //주문 수량

    //
    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    //==비즈니스 로직==//
    public void cancel() {
        //취소시, 재고 업데이트
        getItem().addStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() {
        //주문상품의 전체 가격 반환
        return getOrderPrice() * getCount();
    }
}