package jeawoon.blogproject.service;

import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.entity.item.Clothes;
import jeawoon.blogproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

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
}
