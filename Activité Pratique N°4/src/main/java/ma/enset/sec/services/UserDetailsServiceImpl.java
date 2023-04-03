package ma.enset.sec.services;

import ma.enset.sec.entites.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=securityService.loadByUserName(username);
//        Collection<GrantedAuthority> authorityCollections= new ArrayList();
//        appUser.getRole().forEach(r->{
//           SimpleGrantedAuthority authority= new SimpleGrantedAuthority(r.getRoleName());
//           authorityCollections.add(authority);
//        });
        Collection<GrantedAuthority> authorities=appUser.getRole().stream().map(
                role-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        User user = new User(appUser.getUsername(), appUser.getPassword(), authorities);
        return user;
    }
}
