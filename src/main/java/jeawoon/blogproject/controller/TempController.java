package jeawoon.blogproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TempController {

    @GetMapping("/abc")
    public String tempHome(){
        return "basic/abc";
    }






}
