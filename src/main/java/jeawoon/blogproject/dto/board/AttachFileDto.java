package jeawoon.blogproject.dto.board;

import jeawoon.blogproject.entity.file.AttachFile;
import jeawoon.blogproject.entity.file.AttachType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachFileDto {

    private String uploadFileName; //db 저장
    private String storeFileName; //서버에 저장. 중복이름 고려
    private AttachType attachType;

    public AttachFileDto(AttachFile attachFile){
        this.uploadFileName=attachFile.getUploadFileName();
        this.storeFileName=attachFile.getStoreFileName();
        this.attachType=attachFile.getAttachType();
    }
}
