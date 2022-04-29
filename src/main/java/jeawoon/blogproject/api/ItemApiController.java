package jeawoon.blogproject.api;

import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.service.BoardService;
import jeawoon.blogproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @PutMapping("/shop/clothes/{itemId}/edit")
    public ResponseDto<Integer> update_clothes(@PathVariable("itemId") Long id, @RequestBody ClothesSaveDto dto){
        System.out.println("가나다");
        itemService.updateItem(id, dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }



}
