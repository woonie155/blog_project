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
import java.util.Optional;

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

    public Page<Board> post_list(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board post_detail(long id){
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 id에 매칭되는 board 없음");
        });
    }

    @Transactional
    public void post_delete(long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void post_update(Long id, Board requestBoard){
        Board findBoard = boardRepository.findById(id) //dirty checking용(영속화)
                .orElseThrow(()->{
                    return new IllegalArgumentException("해당 id의 글번호 없음");
                });
        findBoard.setTitle(requestBoard.getTitle());
        findBoard.setContent(requestBoard.getContent());
    }

}
