package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
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
public class CustomerRepository {
    private final SessionFactory sessionFactory;

    public List<Customer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Customer")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<Customer> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Customer where id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<Customer> findByCardNumber(String cardNumber) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Customer where cardNumber = :cardNumber")
                .setParameter("cardNumber", cardNumber)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Customer save(Customer customer) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(customer);
        return customer;
    }

    public void delete(Customer customer) {
        sessionFactory.getCurrentSession()
                .delete(customer);
    }
}
