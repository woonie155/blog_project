package jeawoon.blogproject.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName; //db 저장
    private String storeFileName; //서버에 저장. 중복이름 고려
}
