package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column
    private String email;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(name = "building_num", nullable = false)
    private String buildingNumber;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<PurchaseTransaction> transactions;

    public List<Product> fetchProducts() {
        Hibernate.initialize(products);
        return products;
    }

    public boolean hasAssociatedProducts() {
        // WHY CHECKING ONLY PRODUCTS AND NOT TRANSACTIONS AS WELL?
        // If products there, no need to check if transactions exist,
        // since there cannot be a transaction if there is no product.
        Hibernate.initialize(products);
        return !products.isEmpty();
    }
}
