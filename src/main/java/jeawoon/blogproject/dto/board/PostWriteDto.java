package jeawoon.blogproject.dto.board;

import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.file.AttachType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class PostWriteDto {

    private Long id; //유저 Id
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private Map<AttachType, List<MultipartFile>> attachFiles = new ConcurrentHashMap<>();


    @Builder
    public PostWriteDto(Long id, String title, String content, Map<AttachType, List<MultipartFile>> attachFiles){
        this.id=id;
        this.title=title;
        this.content=content;
        this.attachFiles = attachFiles;
    }





}
