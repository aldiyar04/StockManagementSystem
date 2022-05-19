package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class UserRepository {
    private final SessionFactory sessionFactory;

    public List<User> findAllByRole(User.Role role) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User u where role = :role")
                .setParameter("role", role)
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<User> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User u where u.id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<User> findByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User u where u.email = :email")
                .setParameter("email", email)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<User> findByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public User save(User user) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(user);
        return user;
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession()
                .delete(user);
    }
}
