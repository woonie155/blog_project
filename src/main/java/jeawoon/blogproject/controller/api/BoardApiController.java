package jeawoon.blogproject.controller.api;


import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 글쓰기처리
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.post_write(board, principalDetail.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete_post(@PathVariable("id") Long id){
        System.out.println("abcde");
        boardService.board_delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}