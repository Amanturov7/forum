package kg.amanturov.forum.repository;

import kg.amanturov.forum.model.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {
    List<Repost> findByTicketId(Long ticketId);
    Boolean existsByTicketIdAndUserId(Long ticketId, Long userId);
    Long countByTicketId(Long ticketId);

}