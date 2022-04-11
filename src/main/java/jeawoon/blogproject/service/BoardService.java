package jeawoon.blogproject.service;


import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
