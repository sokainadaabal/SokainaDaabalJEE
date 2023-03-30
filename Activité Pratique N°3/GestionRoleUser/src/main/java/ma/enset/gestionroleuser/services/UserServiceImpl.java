package ma.enset.gestionroleuser.services;

import lombok.AllArgsConstructor;
import ma.enset.gestionroleuser.entities.Role;
import ma.enset.gestionroleuser.entities.User;
import ma.enset.gestionroleuser.repositories.RoleRepository;
import ma.enset.gestionroleuser.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

//@Component // generale prut utiliser dans n'import couche
@Service  // component pour la couche metier
@Transactional // gerer les transactions
@AllArgsConstructor // ne pas utiliser @Autowired pour faire l'injection, on peut utiliser les constructeurs
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString()); // generate une chaine de character qui unique et depend de la date de system en exa decimal
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    // association bidirectional une fois ajouter l'utilisateur a le role il faut ajouter le role a l'utlisateur
    @Override
    public void addRoleToUser(String username, String roleNAme) {
            User user = findUserByUserName(username);
            Role role=findRoleByRoleName(roleNAme);
            if(user.getRoles()!=null)
            {
                user.getRoles().add(role);
                role.getUsers().add(user);
            }
            // userRepository.save(user);
    }

    @Override
    public User Authentification(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user==null) throw new RuntimeException("User not existe");
        if(user.getPassword().equals(password)) return user;
        throw  new RuntimeException("Bad credentails"); // pour dire que l'un des inputs n'est pas bon
    }
}
