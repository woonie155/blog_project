package jeawoon.blogproject.controller;

import jeawoon.blogproject.dto.item.ClothesSaveDto;
import jeawoon.blogproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/shop/main")
    public String main(){
        return "shop/main";
    }

    //아이템 저장 화면접속
    @GetMapping("/shop/clothesSaveForm")
    public String saveSite_enter(Model model){
        model.addAttribute("item", new ClothesSaveDto());
        return "shop/clothesSaveForm";
    }
    @PostMapping("shop/clothes/save")
    public String itemSave(ClothesSaveDto dto){

        itemService.saveItem(dto);
        return "redirect:/";
    }

}
