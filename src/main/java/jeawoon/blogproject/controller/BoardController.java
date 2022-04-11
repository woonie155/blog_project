package jeawoon.blogproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/write")
    public String post_write(){
        return "board/writeForm";
    }



}
