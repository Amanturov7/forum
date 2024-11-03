package kg.amanturov.forum.repository;

import kg.amanturov.forum.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByTicketId(Long ticketId);
    Optional<Like> findByTicketIdAndUserId(Long ticketId, Long userId);
    Optional<Like> findByTicketId(Long ticketId);
    boolean existsByTicketIdAndUserId(Long ticketId, Long userId);
}