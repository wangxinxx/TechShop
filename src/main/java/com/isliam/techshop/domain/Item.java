package com.isliam.techshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.isliam.techshop.domain.enumeration.ItemStatus;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "gtin", length = 13)
    private String gtin;

    @Lob
    @Column(name = "barcode")
    private String barcode;

    @NotNull
    @Column(name = "jhi_cost", nullable = false)
    private Double cost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ItemStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGtin() {
        return gtin;
    }

    public Item gtin(String gtin) {
        this.gtin = gtin;
        return this;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getBarcode() {
        return barcode;
    }

    public Item barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getCost() {
        return cost;
    }

    public Item cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Item status(ItemStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public Item product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        if (item.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", gtin='" + getGtin() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", cost=" + getCost() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
