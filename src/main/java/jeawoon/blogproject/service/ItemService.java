package jeawoon.blogproject.service;

import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.item.Clothes;
import jeawoon.blogproject.entity.item.Item;
import jeawoon.blogproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item item_detail(long id){
        return itemRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 id에 매칭되는 item 없음");
        });
    }

    @Transactional
    public void saveItem(ClothesSaveDto dto) {

        Clothes clothes = Clothes.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .brand(dto.getBrand())
                .build();
        itemRepository.save(clothes);

    }

    @Transactional
    public void updateItem(Long id, ClothesSaveDto dto) {
        Item findItem = itemRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("해당 id의 아이템 없음");
                });
        findItem.setName(dto.getName());
        findItem.setPrice(dto.getPrice());
        findItem.setStockQuantity(dto.getStockQuantity());
    }

}
