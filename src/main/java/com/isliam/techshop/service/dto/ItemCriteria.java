package com.isliam.techshop.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.isliam.techshop.domain.enumeration.ItemStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Item entity. This class is used in ItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemCriteria implements Serializable {
    /**
     * Class for filtering ItemStatus
     */
    public static class ItemStatusFilter extends Filter<ItemStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter gtin;

    private DoubleFilter cost;

    private ItemStatusFilter status;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGtin() {
        return gtin;
    }

    public void setGtin(StringFilter gtin) {
        this.gtin = gtin;
    }

    public DoubleFilter getCost() {
        return cost;
    }

    public void setCost(DoubleFilter cost) {
        this.cost = cost;
    }

    public ItemStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ItemStatusFilter status) {
        this.status = status;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemCriteria that = (ItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gtin, that.gtin) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(status, that.status) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gtin,
        cost,
        status,
        productId
        );
    }

    @Override
    public String toString() {
        return "ItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gtin != null ? "gtin=" + gtin + ", " : "") +
                (cost != null ? "cost=" + cost + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
