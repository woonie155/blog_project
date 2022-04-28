package jeawoon.blogproject.repository;

import jeawoon.blogproject.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
