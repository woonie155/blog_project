package jeawoon.blogproject.entity.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("C")
public class clothes {

    private String brand;


}
