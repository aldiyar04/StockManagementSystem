package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("SALE")
@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SaleTransaction extends Transaction {
    @ManyToOne
    @JoinColumn(name = "customer_id", updatable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Customer customer;
}
