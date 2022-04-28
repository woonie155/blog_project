package jeawoon.blogproject.dto.item;

import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.entity.item.Item;
import lombok.Data;

@Data
public class ClothesSaveDto {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String brand;
}
