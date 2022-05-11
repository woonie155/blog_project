package jeawoon.blogproject.entity.file;

import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Delivery;
import jeawoon.blogproject.entity.OrderType;
import jeawoon.blogproject.entity.item.Item;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttachFile {


    @Id
    @GeneratedValue
    @Column(name = "attach_file_id")
    private Long id;

    private String uploadFileName; //db 저장
    private String storeFileName; //서버에 저장. 중복이름 고려

    @Enumerated(EnumType.STRING)
    private AttachType attachType; //주문상태 [ORDER, CANCEL]

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public AttachFile(String uploadFileName, String storeFileName, AttachType attachType) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.attachType = attachType;
    }

}
