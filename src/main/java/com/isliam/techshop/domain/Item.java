package com.isliam.techshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    @Size(min = 1, max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("items")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPropertyBool> itemPropertyBools = new HashSet<>();
    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPropertyDouble> itemPropertyDoubles = new HashSet<>();
    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPropertyFloat> itemPropertyFloats = new HashSet<>();
    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPropertyInt> itemPropertyInts = new HashSet<>();
    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPropertyString> itemPropertyStrings = new HashSet<>();
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

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Item active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Item manufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<ItemPropertyBool> getItemPropertyBools() {
        return itemPropertyBools;
    }

    public Item itemPropertyBools(Set<ItemPropertyBool> itemPropertyBools) {
        this.itemPropertyBools = itemPropertyBools;
        return this;
    }

    public Item addItemPropertyBool(ItemPropertyBool itemPropertyBool) {
        this.itemPropertyBools.add(itemPropertyBool);
        itemPropertyBool.setItem(this);
        return this;
    }

    public Item removeItemPropertyBool(ItemPropertyBool itemPropertyBool) {
        this.itemPropertyBools.remove(itemPropertyBool);
        itemPropertyBool.setItem(null);
        return this;
    }

    public void setItemPropertyBools(Set<ItemPropertyBool> itemPropertyBools) {
        this.itemPropertyBools = itemPropertyBools;
    }

    public Set<ItemPropertyDouble> getItemPropertyDoubles() {
        return itemPropertyDoubles;
    }

    public Item itemPropertyDoubles(Set<ItemPropertyDouble> itemPropertyDoubles) {
        this.itemPropertyDoubles = itemPropertyDoubles;
        return this;
    }

    public Item addItemPropertyDouble(ItemPropertyDouble itemPropertyDouble) {
        this.itemPropertyDoubles.add(itemPropertyDouble);
        itemPropertyDouble.setItem(this);
        return this;
    }

    public Item removeItemPropertyDouble(ItemPropertyDouble itemPropertyDouble) {
        this.itemPropertyDoubles.remove(itemPropertyDouble);
        itemPropertyDouble.setItem(null);
        return this;
    }

    public void setItemPropertyDoubles(Set<ItemPropertyDouble> itemPropertyDoubles) {
        this.itemPropertyDoubles = itemPropertyDoubles;
    }

    public Set<ItemPropertyFloat> getItemPropertyFloats() {
        return itemPropertyFloats;
    }

    public Item itemPropertyFloats(Set<ItemPropertyFloat> itemPropertyFloats) {
        this.itemPropertyFloats = itemPropertyFloats;
        return this;
    }

    public Item addItemPropertyFloat(ItemPropertyFloat itemPropertyFloat) {
        this.itemPropertyFloats.add(itemPropertyFloat);
        itemPropertyFloat.setItem(this);
        return this;
    }

    public Item removeItemPropertyFloat(ItemPropertyFloat itemPropertyFloat) {
        this.itemPropertyFloats.remove(itemPropertyFloat);
        itemPropertyFloat.setItem(null);
        return this;
    }

    public void setItemPropertyFloats(Set<ItemPropertyFloat> itemPropertyFloats) {
        this.itemPropertyFloats = itemPropertyFloats;
    }

    public Set<ItemPropertyInt> getItemPropertyInts() {
        return itemPropertyInts;
    }

    public Item itemPropertyInts(Set<ItemPropertyInt> itemPropertyInts) {
        this.itemPropertyInts = itemPropertyInts;
        return this;
    }

    public Item addItemPropertyInt(ItemPropertyInt itemPropertyInt) {
        this.itemPropertyInts.add(itemPropertyInt);
        itemPropertyInt.setItem(this);
        return this;
    }

    public Item removeItemPropertyInt(ItemPropertyInt itemPropertyInt) {
        this.itemPropertyInts.remove(itemPropertyInt);
        itemPropertyInt.setItem(null);
        return this;
    }

    public void setItemPropertyInts(Set<ItemPropertyInt> itemPropertyInts) {
        this.itemPropertyInts = itemPropertyInts;
    }

    public Set<ItemPropertyString> getItemPropertyStrings() {
        return itemPropertyStrings;
    }

    public Item itemPropertyStrings(Set<ItemPropertyString> itemPropertyStrings) {
        this.itemPropertyStrings = itemPropertyStrings;
        return this;
    }

    public Item addItemPropertyString(ItemPropertyString itemPropertyString) {
        this.itemPropertyStrings.add(itemPropertyString);
        itemPropertyString.setItem(this);
        return this;
    }

    public Item removeItemPropertyString(ItemPropertyString itemPropertyString) {
        this.itemPropertyStrings.remove(itemPropertyString);
        itemPropertyString.setItem(null);
        return this;
    }

    public void setItemPropertyStrings(Set<ItemPropertyString> itemPropertyStrings) {
        this.itemPropertyStrings = itemPropertyStrings;
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
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
