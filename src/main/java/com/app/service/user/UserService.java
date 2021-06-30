package com.app.service.user;

import com.app.entity.AppRole;
import com.app.entity.AppUser;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.findByName(username);

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (AppRole role: appUser.getRoleList()
             ) {
            authorities.add(role);
        }
        UserDetails userDetails = new User(appUser.getUserName(),appUser.getUserPassword(),authorities);

        return userDetails;
    }

    public AppUser findByName(String name){
        return userRepository.findByUserName(name);
    }

    @Override
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public void remove(Long id) {
      userRepository.deleteById(id);
    }
}
