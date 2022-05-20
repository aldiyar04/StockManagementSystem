package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.Uom;
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
public class ProductRepository {
    private final SessionFactory sessionFactory;

    public List<Product> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Product")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<Product> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Product where id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<Product> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Product where name = :name")
                .setParameter("name", name)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Product save(Product product) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(product);
        return product;
    }

    public Uom persistUom(Uom uom) {
        sessionFactory.getCurrentSession()
                .persist(uom);
        return uom;
    }

    public void delete(Product product) {
        sessionFactory.getCurrentSession()
                .delete(product);
    }
}
