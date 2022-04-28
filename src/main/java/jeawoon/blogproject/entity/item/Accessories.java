package jeawoon.blogproject.entity.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Accessories {

    private String material;

}
