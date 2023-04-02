package ma.enset.sec.services;

import groovy.util.logging.Slf4j;

import lombok.AllArgsConstructor;
import ma.enset.sec.entites.AppRole;
import ma.enset.sec.entites.AppUser;
import ma.enset.sec.repositories.AppRoleRepository;
import ma.enset.sec.repositories.AppUSerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@Slf4j //permet de logger des informations
@AllArgsConstructor
@Transactional
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
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole!=null) throw  new RuntimeException("Role "+ roleName +"Deja existe");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole appRoleSaved= appRoleRepository.save(appRole);
        return appRoleSaved;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
           AppUser appUser= appUSerRepository.findByUsername(userName);
           if (appUser==null)  throw new RuntimeException("User not found");
           AppRole appRole = appRoleRepository.findByRoleName(roleName);
           if (appRole==null)  throw new RuntimeException("Role not found");
           appUser.getRole().add(appRole);
           // appUSerRepository.save(appUser); // n'est obligatoire de le faire
    }

    @Override
    public void removeRoleFromUser(String userName, String roleName) {
        AppUser appUser= appUSerRepository.findByUsername(userName);
        if (appUser==null)  throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole==null)  throw new RuntimeException("Role not found");
        appUser.getRole().remove(appRole);
        // appUSerRepository.save(appUser); // n'est obligatoire de le faire
    }

    @Override
    public AppUser loadByUserName(String username) {

        return appUSerRepository.findByUsername(username);
    }
}
