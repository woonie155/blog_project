package jeawoon.blogproject.service;


import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Reply;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.BoardRepository;
import jeawoon.blogproject.repository.ReplyRepository;
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
    private final ReplyRepository replyRepository;


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

    @Transactional
    public void reply_save(User user, Long boardId, Reply reply) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 ID를 찾을 수 없습니다.");
                });
        reply.setUser(user);
        reply.setBoard(board);

        replyRepository.save(reply);
    }
}
