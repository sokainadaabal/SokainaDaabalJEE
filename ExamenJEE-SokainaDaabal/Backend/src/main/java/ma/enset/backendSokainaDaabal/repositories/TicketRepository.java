package ma.enset.backendSokainaDaabal.repositories;


import ma.enset.backendSokainaDaabal.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
