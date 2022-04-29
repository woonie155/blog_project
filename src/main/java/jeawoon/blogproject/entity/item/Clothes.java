package jeawoon.blogproject.entity.item;

import jeawoon.blogproject.entity.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("C")
@NoArgsConstructor
public class Clothes extends Item{

    private String brand;

    @Builder
    public Clothes(Long id, String name, int price, int stockQuantity, String brand){
        super(id, name, price, stockQuantity);
        this.brand=brand;
    }

}
