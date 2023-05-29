package ma.enset.ebankingbackend.security.repositories;

import ma.enset.ebankingbackend.security.entities.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
