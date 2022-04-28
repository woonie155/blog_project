package jeawoon.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchAllUser_ResponseDto<T> {

    private int count;
    private T data;

}
