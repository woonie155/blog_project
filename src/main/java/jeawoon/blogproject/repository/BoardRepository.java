package jeawoon.blogproject.repository;

import jeawoon.blogproject.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Board> findDetailById(Long id);

    @Query(value = "select b from Board b join fetch b.user u", countQuery = "select count(b) from Board b")
    Page<Board> findBoardListBy(Pageable pageable);
}
