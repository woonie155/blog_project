package jeawoon.blogproject.controller;

import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.entity.item.Item;
import jeawoon.blogproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //아이템 메인화면
    @GetMapping("/shop/main")
    public String main(Model model){
        List<Item> items= itemService.findAll();
        model.addAttribute("items", items);
        return "shop/main";
    }

    //아이템 저장 화면접속
    @GetMapping("/shop/clothesSaveForm")
    public String clothesSaveSite_enter(Model model){
        model.addAttribute("item", new ClothesSaveDto());
        return "shop/clothesSaveForm";
    } //아이템 저장
    @PostMapping("shop/clothes/save")
    public String clothesSave(ClothesSaveDto dto){

        itemService.saveItem(dto);
        return "redirect:/shop/main";
    }

    //아이템 수정화면 접속
    @GetMapping("/shop/clothes/{itemId}/edit")
    public String clothesUpdateSite_Form(@PathVariable("itemId") Long itemId, Model model) {

        model.addAttribute("dto", itemService.item_detail(itemId));
        return "shop/clothesUpdateForm";
    }


}
