package jeawoon.blogproject.controller;


import jeawoon.blogproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/write")
    public String post_write(){
        return "board/writeForm";
    }

    @GetMapping("/board/{id}")
    public String board_detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.board_detail(id));
        return "board/detail";
    }



}
