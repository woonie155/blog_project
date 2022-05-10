package jeawoon.blogproject.entity.item;

import jeawoon.blogproject.entity.file.ImageFiles;
import jeawoon.blogproject.entity.file.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorValue("C")
@NoArgsConstructor
public class Clothes extends Item{

    private String brand;

    @Builder
    public Clothes(Long id, String name, int price, int stockQuantity, List<ImageFiles> imageFiles, String brand){
        super(id, name, price, stockQuantity, imageFiles);
        this.brand=brand;
    }

}
