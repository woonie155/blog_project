package jeawoon.blogproject.controller;

import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.service.BoardService;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;

    @GetMapping({"", "/"})
    public String main(){
        return "home";
    }
    @GetMapping("/auth/join")
    public String joinForm(){
        return "user/joinForm";
    }
    @RequestMapping("/auth/login")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/detail") //업데이트
    public String detail(Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("principal", principal.getUser());
        return "user/detailForm";
    }
}
