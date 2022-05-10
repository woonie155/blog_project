package jeawoon.blogproject.entity.file;

import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Delivery;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class AttachFile {


    @Id
    @GeneratedValue
    @Column(name = "attach_file_id")
    private Long id;


//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "board_id")
//    private Board board;
}
