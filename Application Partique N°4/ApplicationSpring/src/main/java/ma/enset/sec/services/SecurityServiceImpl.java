package ma.enset.sec.services;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.enset.sec.entites.AppRole;
import ma.enset.sec.entites.AppUser;
import ma.enset.sec.repositories.AppRoleRepository;
import ma.enset.sec.repositories.AppUSerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j //permet de logger des informations
@AllArgsConstructor

public class SecurityServiceImpl implements SecurityService {
    private AppUSerRepository appUSerRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)) throw new RuntimeException("Password not match"); // recommander de creer des exceptions personnaliser
        String hachePWD = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString()); // generer des ids selon le temps ce que assure que il est unique
        appUser.setUsername(username);
        appUser.setPassword(hachePWD);
        appUser.setActive(true);
        AppUser savedAppUSer=appUSerRepository.save(appUser);
        return savedAppUSer;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        return null;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {

    }

    @Override
    public void removeRoleFromUser(String userName, String roleName) {

    }

    @Override
    public AppUser loadByUserName(String username) {
        return null;
    }
}
