package cinemas.repositories.impl;

import cinemas.models.User;
import cinemas.repositories.UserRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository("userRepository")
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Integer> implements UserRepository {
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        String hql = "FROM User u WHERE u.email = :email";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("email", email);

        return query.getSingleResult();
    }
}
