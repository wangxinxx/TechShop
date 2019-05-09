package com.isliam.techshop.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.isliam.techshop.domain.enumeration.OperationType;
import com.isliam.techshop.domain.enumeration.OperationState;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Operation entity. This class is used in OperationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperationCriteria implements Serializable {
    /**
     * Class for filtering OperationType
     */
    public static class OperationTypeFilter extends Filter<OperationType> {
    }
    /**
     * Class for filtering OperationState
     */
    public static class OperationStateFilter extends Filter<OperationState> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private OperationTypeFilter type;

    private OperationStateFilter state;

    private StringFilter description;

    private LocalDateFilter orderDate;

    private LocalDateFilter approveDate;

    private LocalDateFilter deliveryDate;

    private LongFilter customerId;

    private LongFilter sellerId;

    private LongFilter curierId;

    private LongFilter itemId;

    private LongFilter addressId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public OperationTypeFilter getType() {
        return type;
    }

    public void setType(OperationTypeFilter type) {
        this.type = type;
    }

    public OperationStateFilter getState() {
        return state;
    }

    public void setState(OperationStateFilter state) {
        this.state = state;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateFilter orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateFilter getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDateFilter approveDate) {
        this.approveDate = approveDate;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getSellerId() {
        return sellerId;
    }

    public void setSellerId(LongFilter sellerId) {
        this.sellerId = sellerId;
    }

    public LongFilter getCurierId() {
        return curierId;
    }

    public void setCurierId(LongFilter curierId) {
        this.curierId = curierId;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OperationCriteria that = (OperationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(state, that.state) &&
            Objects.equals(description, that.description) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(approveDate, that.approveDate) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(sellerId, that.sellerId) &&
            Objects.equals(curierId, that.curierId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        state,
        description,
        orderDate,
        approveDate,
        deliveryDate,
        customerId,
        sellerId,
        curierId,
        itemId,
        addressId
        );
    }

    @Override
    public String toString() {
        return "OperationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (approveDate != null ? "approveDate=" + approveDate + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (sellerId != null ? "sellerId=" + sellerId + ", " : "") +
                (curierId != null ? "curierId=" + curierId + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
            "}";
    }

}
