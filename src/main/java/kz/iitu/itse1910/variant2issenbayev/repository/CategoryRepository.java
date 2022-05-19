package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.Category;
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
public class CategoryRepository {
    private final SessionFactory sessionFactory;

    public List<Category> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Category")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<Category> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Category where id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Optional<Category> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Category where name = :name")
                .setParameter("name", name)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Category save(Category Category) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(Category);
        return Category;
    }

    public void delete(Category Category) {
        sessionFactory.getCurrentSession()
                .delete(Category);
    }
}
