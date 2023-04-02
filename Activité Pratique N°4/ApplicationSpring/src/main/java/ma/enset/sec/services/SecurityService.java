package ma.enset.sec.services;

import ma.enset.sec.entites.AppRole;
import ma.enset.sec.entites.AppUser;

public interface SecurityService {

    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String userName,String roleName);
    void removeRoleFromUser(String userName,String roleName);
    AppUser loadByUserName(String username);

}
