package jeawoon.blogproject.service;


import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
    public void post_write(Board board, User user){
        board.setUser(user);
        board.setViewCount(0);
        boardRepository.save(board);
    }

    public Page<Board> board_list(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board board_detail(long id){
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 id에 매칭되는 board 없음");
        });
    }
}
