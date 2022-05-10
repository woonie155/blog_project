package jeawoon.blogproject.entity.file;

import jeawoon.blogproject.entity.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class ImageFiles {

    @Id
    @GeneratedValue
    @Column(name = "image_files_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String uploadFileName; //db 저장
    private String storeFileName; //서버에 저장. 중복이름 고려
}
