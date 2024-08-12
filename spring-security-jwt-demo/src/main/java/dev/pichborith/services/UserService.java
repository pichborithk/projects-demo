package dev.pichborith.services;

import dev.pichborith.models.User;
import dev.pichborith.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public Boolean checkUserExistenceByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public User addUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (email.isBlank() || password.isBlank()) {
            return null;
        }

        return userRepo.save(user);
    }
}
