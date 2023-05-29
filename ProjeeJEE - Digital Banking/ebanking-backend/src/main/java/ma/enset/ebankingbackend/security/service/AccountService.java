package ma.enset.ebankingbackend.security.service;

import ma.enset.ebankingbackend.security.entities.AppRole;
import ma.enset.ebankingbackend.security.entities.AppUser;

import java.util.List;

public interface AccountService {
AppUser saveNewUser(AppUser appUser);
AppRole saveNewRole(AppRole appRole);
void addRoleToUser(String userName,String roleName);
void removeRoleFromUser(String userName,String roleName);
AppUser loadByUserName(String username);
List<AppUser> listUsers();
// login
AppUser login(AppUser appUser);

}
