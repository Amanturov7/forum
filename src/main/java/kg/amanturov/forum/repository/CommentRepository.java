package kg.amanturov.forum.repository;

import kg.amanturov.forum.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicketId(Long ticketId);
    Optional<Comment> findByIdAndUserId(Long ticketId, Long userId);
    Long countByTicketId(Long ticketId);

}
