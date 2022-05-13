package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(user);
    }
}
