package jeawoon.blogproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jeawoon.blogproject.entity.file.AttachFile;
import jeawoon.blogproject.entity.file.ImageFiles;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<AttachFile> attachFiles = new ArrayList<>(); //

//    @ColumnDefault("0")
    private int viewCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id")
    private List<Reply> reply;

    //
    public Board (User user, String title, String content){
        this.user=user;
        this.title=title;
        this.content=content;
    }
    public Board (String title, String content, List<AttachFile> attachFiles){
        this.title=title;
        this.content=content;
        this.attachFiles = attachFiles;
    }

    public void setAttachFile(AttachFile attachFile) {
        this.attachFiles.add(attachFile);
        attachFile.setBoard(this);
    }

}
