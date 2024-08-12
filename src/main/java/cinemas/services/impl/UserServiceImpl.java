package cinemas.services.impl;

import cinemas.models.User;
import cinemas.repositories.UserRepository;
import cinemas.services.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User save(User user) {
        if (userRepository != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
