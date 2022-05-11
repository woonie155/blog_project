package jeawoon.blogproject.repository;

import jeawoon.blogproject.entity.file.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<AttachFile, Long> {

}
