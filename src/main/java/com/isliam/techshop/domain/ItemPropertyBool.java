package com.isliam.techshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ItemPropertyBool.
 */
@Entity
@Table(name = "item_property_bool")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPropertyBool implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private Boolean value;

    @ManyToOne(optional = false)
    @NotNull
    private Item item;

    @ManyToOne(optional = false)
    @NotNull
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isValue() {
        return value;
    }

    public ItemPropertyBool value(Boolean value) {
        this.value = value;
        return this;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public ItemPropertyBool item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Property getProperty() {
        return property;
    }

    public ItemPropertyBool property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
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
        ItemPropertyBool itemPropertyBool = (ItemPropertyBool) o;
        if (itemPropertyBool.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyBool.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyBool{" +
            "id=" + getId() +
            ", value='" + isValue() + "'" +
            "}";
    }
}
