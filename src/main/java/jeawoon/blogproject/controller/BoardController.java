package jeawoon.blogproject.controller;


import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/write")
    public String post_write(@AuthenticationPrincipal PrincipalDetail principalDetail){
        System.out.println("user = " + principalDetail.getUsername());
        return "board/writeForm";
    }


    @GetMapping("/board/{id}")
    public String post_detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.post_detail(id));
        return "board/detail";
    }

    @GetMapping("board/{id}/update")
    public String post_update(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.post_detail(id));
        return "board/updateForm";
    }



}
