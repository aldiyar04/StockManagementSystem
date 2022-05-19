package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
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
public class SupplierRepository {
    private final SessionFactory sessionFactory;

    public List<Supplier> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Supplier")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<Supplier> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Supplier where id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<Supplier> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Supplier where name = :name")
                .setParameter("name", name)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Supplier save(Supplier supplier) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(supplier);
        return supplier;
    }

    public void delete(Supplier supplier) {
        sessionFactory.getCurrentSession()
                .delete(supplier);
    }
}
