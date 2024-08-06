package cinemas.repositories.impl;

import cinemas.models.User;
import cinemas.repositories.UserRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Integer> implements UserRepository {
    public UserRepositoryImpl() {
        super(User.class);
    }
}
