package jeawoon.blogproject.dto.board;


import jeawoon.blogproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {

    private Long id; //boardId
    private String content;
    private String title;
    private User user;
    private String nickname;


}
