package jeawoon.blogproject.dto.board;

import jeawoon.blogproject.entity.file.AttachType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class PostWriteForm {


    private Long id; //유저 ID
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> uploadFiles;

    @Builder
    public PostWriteForm(Long id, String title, String content,List<MultipartFile> imageFiles, List<MultipartFile> uploadFiles){
        this.id=id;
        this.title=title;
        this.content=content;
        this.imageFiles = (imageFiles!=null) ? imageFiles : new ArrayList<>();
        this.uploadFiles = (uploadFiles!=null) ? uploadFiles : new ArrayList<>();
    }

    public PostWriteDto createPostWriteDto(){
        Map<AttachType, List<MultipartFile>> attach = getAttachTypeListMap();
        return PostWriteDto.builder().id(id).title(title).content(content).attachFiles(attach).build();
    }

    private Map<AttachType, List<MultipartFile>> getAttachTypeListMap() {
        Map<AttachType, List<MultipartFile>> attachments = new HashMap<>();
        attachments.put(AttachType.IMAGE, imageFiles);
        attachments.put(AttachType.FILE, uploadFiles);
        return attachments;
    }

}