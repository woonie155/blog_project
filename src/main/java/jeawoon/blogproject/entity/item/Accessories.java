package jeawoon.blogproject.entity.item;

import jeawoon.blogproject.entity.file.ImageFiles;
import jeawoon.blogproject.entity.file.UploadFile;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("A")
@Getter
public class Accessories extends Item{

    private String material;

    @Builder
    public Accessories(Long id, String name, int price, int stockQuantity, List<ImageFiles> imageFiles, String material){
        super(id, name, price, stockQuantity, imageFiles);
        this.material=material;
    }
}
