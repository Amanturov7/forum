package kg.amanturov.forum.repository;

import kg.amanturov.forum.model.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachments, Long> {

//    Attachments findByApplicationsId(Long id);
    Attachments findByCommentId(Long id);
    Attachments findByTicketsId(Long id);
    Attachments findByUserIdAndType(Long id, String type);
    Optional<Attachments> findById(Long id);
}
