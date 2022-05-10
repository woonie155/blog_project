package jeawoon.blogproject.controller;


import jeawoon.blogproject.config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.order.OrderListExcludeItemDto;
import jeawoon.blogproject.repository.ReplyRepository;
import jeawoon.blogproject.service.BoardService;
import jeawoon.blogproject.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/board/main")
    public String main(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.post_list(pageable));
        return "board/main";
    }

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

    @GetMapping("/board/{id}/update")
    public String post_update(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.post_detail(id));
        return "board/updateForm";
    }



}
