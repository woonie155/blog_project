package jeawoon.blogproject.controller;

import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.entity.item.Item;
import jeawoon.blogproject.service.ItemService;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final ItemService itemService;

    //주문하기 페이지 접속
    @GetMapping("/shop/orderSaveForm")
    public String orderSaveForm(Model model){

        List<User> members = userService.findUsers();
        List<Item> items = itemService.findAll();

        model.addAttribute("users", members);
        model.addAttribute("items", items);
        return "shop/orderSaveForm";
    }

    //주문목록 페이지 접속
    @GetMapping("/shop/orderListForm")
    public String orderListForm(){
        return "shop/orderListForm";
    }



}
