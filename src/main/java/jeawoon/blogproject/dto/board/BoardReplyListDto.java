package jeawoon.blogproject.dto.board;

import jeawoon.blogproject.entity.Reply;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BoardReplyListDto {

    private Long r_id;
    private String r_content;
    private String r_username;
    private String r_loginId; //댓글 작성자 아이디


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime r_createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime r_lastModifiedDate;

    public BoardReplyListDto(Reply reply){
        this.r_id = reply.getId();
        this.r_content = reply.getContent();
        this.r_username = reply.getUser().getUsername();
        this.r_loginId = reply.getUser().getLoginId();
        this.r_createdDate = reply.getCreatedDate();
        this.r_lastModifiedDate = reply.getLastModifiedDate();
    }
}
