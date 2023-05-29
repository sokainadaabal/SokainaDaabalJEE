package ma.enset.ebankingbackend.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.security.entities.AppRole;
import ma.enset.ebankingbackend.security.entities.AppUser;
import ma.enset.ebankingbackend.security.repositories.RoleRepository;
import ma.enset.ebankingbackend.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private  UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser saveNewUser(AppUser appUser) {
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public AppRole saveNewRole(AppRole appRole) {
        return roleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
       AppRole role = roleRepository.findByRoleName(roleName);
       AppUser user = userRepository.findByUsername(userName);
       user.getRole().add(role);
    }

    @Override
    public void removeRoleFromUser(String userName, String roleName) {
        AppRole role = roleRepository.findByRoleName(roleName);
        AppUser user = userRepository.findByUsername(userName);
        user.getRole().remove(role);
    }

    @Override
    public AppUser loadByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser login(AppUser appUser) {
        AppUser user= userRepository.findByUsername(appUser.getUsername());
        if (user== null) return  null;
        else if (passwordEncoder.matches(appUser.getPassword(),user.getPassword())) return  null;
        return user;
    }
}
