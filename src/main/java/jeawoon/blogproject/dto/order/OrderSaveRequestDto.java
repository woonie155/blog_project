package jeawoon.blogproject.dto.order;

import lombok.Data;

@Data
public class OrderSaveRequestDto {

    private Long id;
    private Long itemId;
    private int count;
}
