package jeawoon.blogproject.controller;

import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
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
    public String main(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.post_list(pageable));
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

    @GetMapping("/user/detail")
    public String detail(Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("principal", principal.getUser());
        return "user/detailForm";
    }


}
