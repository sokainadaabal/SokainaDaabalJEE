package ma.enset.backendSokainaDaabal.repositories;

import ma.enset.backendSokainaDaabal.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
