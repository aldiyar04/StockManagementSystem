package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import kz.iitu.itse1910.variant2issenbayev.entity.PurchaseTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class TransactionRepository {
    private final SessionFactory sessionFactory;

    public List<PurchaseTransaction> findAllPurchases() {
        return sessionFactory.getCurrentSession()
                .createQuery("from PurchaseTransaction")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public List<SaleTransaction> findAllPurchasesBySupplier(Supplier supplier) {
        return sessionFactory.getCurrentSession()
                .createQuery("from PurchaseTransaction where supplier = :supplier")
                .setParameter("supplier", supplier)
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public List<SaleTransaction> findAllSales() {
        return sessionFactory.getCurrentSession()
                .createQuery("from SaleTransaction")
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public List<SaleTransaction> findAllSalesByCustomer(Customer customer) {
        return sessionFactory.getCurrentSession()
                .createQuery("from SaleTransaction where customer = :customer")
                .setParameter("customer", customer)
                .setHint("org.hibernate.cacheable", "true")
                .list();
    }

    public Optional<Transaction> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Transaction where id = :id")
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", "true")
                .uniqueResultOptional();
    }

    public Transaction save(Transaction transaction) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(transaction);
        return transaction;
    }

    public void delete(Transaction transaction) {
        sessionFactory.getCurrentSession()
                .delete(transaction);
    }
}
