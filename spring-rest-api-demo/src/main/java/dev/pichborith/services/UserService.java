package dev.pichborith.services;

import dev.pichborith.models.User;
import dev.pichborith.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    public User getUserById(int userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Transactional
    public User addUser(User newUser) {
        String username = newUser.getUsername();
        String password = newUser.getPassword();
        if (username.isBlank() || password.length() < 3) {
            return null;
        }

        return userRepo.save(newUser);
    }

    public Boolean checkUserExistenceByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(
        String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                       .orElseThrow(() -> new UsernameNotFoundException(
                           "Username not found"));
    }
}
