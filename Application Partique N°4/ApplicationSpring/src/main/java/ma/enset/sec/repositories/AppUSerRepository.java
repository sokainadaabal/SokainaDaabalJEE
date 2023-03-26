package ma.enset.sec.repositories;


import ma.enset.sec.entites.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUSerRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
