package jeawoon.blogproject.api;


import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Reply;
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
        boardService.post_delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update_post(@PathVariable("id") Long id, @RequestBody Board board){
        boardService.post_update(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> save_reply(@PathVariable("boardId") Long boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.reply_save(principalDetail.getUser(), boardId, reply);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> delete_reply(@PathVariable("boardId") Long boardId, @PathVariable("replyId") Long replyId){
        boardService.reply_delete(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}