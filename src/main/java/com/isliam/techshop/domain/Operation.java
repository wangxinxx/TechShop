package com.isliam.techshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.isliam.techshop.domain.enumeration.OperationType;

import com.isliam.techshop.domain.enumeration.OperationState;

/**
 * A Operation.
 */
@Entity
@Table(name = "operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private OperationType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private OperationState state;

    @Column(name = "description")
    private String description;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("operations")
    private Profile customer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("operations")
    private Profile seller;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private Profile curier;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public Operation type(OperationType type) {
        this.type = type;
        return this;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public OperationState getState() {
        return state;
    }

    public Operation state(OperationState state) {
        this.state = state;
        return this;
    }

    public void setState(OperationState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public Operation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Operation orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public Operation approveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
        return this;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Operation deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Profile getCustomer() {
        return customer;
    }

    public Operation customer(Profile profile) {
        this.customer = profile;
        return this;
    }

    public void setCustomer(Profile profile) {
        this.customer = profile;
    }

    public Profile getSeller() {
        return seller;
    }

    public Operation seller(Profile profile) {
        this.seller = profile;
        return this;
    }

    public void setSeller(Profile profile) {
        this.seller = profile;
    }

    public Profile getCurier() {
        return curier;
    }

    public Operation curier(Profile profile) {
        this.curier = profile;
        return this;
    }

    public void setCurier(Profile profile) {
        this.curier = profile;
    }

    public Item getItem() {
        return item;
    }

    public Operation item(Item item) {
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", approveDate='" + getApproveDate() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            "}";
    }
}
