package jeawoon.blogproject.service;

import jeawoon.blogproject.entity.Board;
import jeawoon.blogproject.entity.Reply;
import jeawoon.blogproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;


}
