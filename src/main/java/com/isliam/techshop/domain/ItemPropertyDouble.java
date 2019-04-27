package com.isliam.techshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ItemPropertyDouble.
 */
@Entity
@Table(name = "item_property_double")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPropertyDouble implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private Double value;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemPropertyDoubles")
    private Property property;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemPropertyDoubles")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public ItemPropertyDouble value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public ItemPropertyDouble property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Item getItem() {
        return item;
    }

    public ItemPropertyDouble item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
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
        ItemPropertyDouble itemPropertyDouble = (ItemPropertyDouble) o;
        if (itemPropertyDouble.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyDouble.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyDouble{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
