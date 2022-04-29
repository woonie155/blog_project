package jeawoon.blogproject.entity.item;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Accessories extends Item{

    private String material;

    @Builder
    public Accessories(Long id, String name, int price, int stockQuantity, String material){
        super(id, name, price, stockQuantity);
        this.material=material;
    }
}