package jeawoon.blogproject.dto.board;

import jeawoon.blogproject.entity.file.AttachFile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostDetailForm {

    private Long id;
    private String title;
    private String content;
    private List<AttachFile> imageFiles;
    private List<AttachFile> uploadFiles;

    @Builder
    public PostDetailForm(Long id, String title, String content, List<AttachFile> imageFiles, List<AttachFile> uploadFiles) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageFiles = imageFiles;
        this.uploadFiles = uploadFiles;
    }

}
