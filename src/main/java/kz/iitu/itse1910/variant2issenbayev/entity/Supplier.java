package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suppliers")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true)
    private String email;

    @Column(unique = true, name = "website_url")
    private String websiteUrl;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(name = "building_num", nullable = false)
    private String buildingNumber;
}
