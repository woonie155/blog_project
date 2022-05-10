package jeawoon.blogproject.dto.board;


import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {

    private Long id; //boardId
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private String username;
    private String loginId;


    private List<BoardReplyListDto> replys;

    public BoardDetailDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.lastModifiedDate = board.getLastModifiedDate();
        this.username = board.getUser().getUsername();
        this.loginId = board.getUser().getLoginId();

        this.replys = board.getReply().stream()
                .map(b -> new BoardReplyListDto(b)).collect(Collectors.toList());
    }


}
