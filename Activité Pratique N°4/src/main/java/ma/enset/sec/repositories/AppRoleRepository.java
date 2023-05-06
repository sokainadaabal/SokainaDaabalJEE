package ma.enset.sec.repositories;

import ma.enset.sec.entites.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
       AppRole  findByRoleName(String roleName);
}
