package jeawoon.blogproject.service;


import jeawoon.blogproject.dto.board.BoardDetailDto;
import jeawoon.blogproject.dto.board.BoardListDto;
import jeawoon.blogproject.dto.board.PostWriteDto;
import jeawoon.blogproject.dto.order.OrderListExcludeItemDto;
import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Order;
import jeawoon.blogproject.entity.Reply;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.entity.file.AttachFile;
import jeawoon.blogproject.repository.BoardRepository;
import jeawoon.blogproject.repository.ReplyRepository;
import jeawoon.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final AttachService attachService;

    @Transactional
    public Long post_write(PostWriteDto dto) throws IOException {
        List<AttachFile> attachFiles = attachService.saveAttachments(dto.getAttachFiles()); //저장
        Board board = new Board(dto.getTitle(), dto.getContent(), new ArrayList<>());

        attachFiles.stream()
                .forEach(af -> board.setAttachFile(af));
        User user = userRepository.findById(dto.getId()).orElseThrow(()->{
            return new IllegalArgumentException("해당 id에 매칭되는 사용자정보 없음");
        });
        board.setUser(user);
        board.setViewCount(0);
        Board result = boardRepository.save(board);
        return result.getId();
    }

    public Page<BoardListDto> post_list(Pageable pageable){
        Page<Board> page = boardRepository.findBoardListBy(pageable);
        return page.map(b -> new BoardListDto(b.getId(), b.getContent(), b.getTitle(), b.getUser(), b.getUser().getNickname()));
    }


    public BoardDetailDto post_detail(long id){
        Board detail = boardRepository.findDetailById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 id에 매칭되는 board 없음");
        });

        BoardDetailDto result = new BoardDetailDto(detail);
        return result;
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

    @Transactional
    public void reply_delete(long replyId){
        replyRepository.deleteById(replyId);
    }

}
