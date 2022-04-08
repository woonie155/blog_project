package jeawoon.blogproject.controller;

import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String main(){
        return "home";
    }
    @GetMapping("/auth/join")
    public String joinForm(){
        return "user/joinForm";
    }
    @GetMapping("/auth/login")
    public String loginForm(){
        return "user/loginForm";
    }



}
