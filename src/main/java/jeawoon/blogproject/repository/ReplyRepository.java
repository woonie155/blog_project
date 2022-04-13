package jeawoon.blogproject.repository;

import jeawoon.blogproject.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
