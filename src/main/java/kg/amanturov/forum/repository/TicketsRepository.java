package kg.amanturov.forum.repository;


import kg.amanturov.forum.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {
}
