package com.isliam.techshop.service.dto;

import java.io.Serializable;
import java.util.Objects;
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter gtin;

    private DoubleFilter cost;

    private StringFilter name;

    private BooleanFilter active;

    private LongFilter productId;

    private LongFilter manufacturerId;

    private LongFilter itemPropertyBoolId;

    private LongFilter itemPropertyDoubleId;

    private LongFilter itemPropertyFloatId;

    private LongFilter itemPropertyIntId;

    private LongFilter itemPropertyStringId;

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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(LongFilter manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public LongFilter getItemPropertyBoolId() {
        return itemPropertyBoolId;
    }

    public void setItemPropertyBoolId(LongFilter itemPropertyBoolId) {
        this.itemPropertyBoolId = itemPropertyBoolId;
    }

    public LongFilter getItemPropertyDoubleId() {
        return itemPropertyDoubleId;
    }

    public void setItemPropertyDoubleId(LongFilter itemPropertyDoubleId) {
        this.itemPropertyDoubleId = itemPropertyDoubleId;
    }

    public LongFilter getItemPropertyFloatId() {
        return itemPropertyFloatId;
    }

    public void setItemPropertyFloatId(LongFilter itemPropertyFloatId) {
        this.itemPropertyFloatId = itemPropertyFloatId;
    }

    public LongFilter getItemPropertyIntId() {
        return itemPropertyIntId;
    }

    public void setItemPropertyIntId(LongFilter itemPropertyIntId) {
        this.itemPropertyIntId = itemPropertyIntId;
    }

    public LongFilter getItemPropertyStringId() {
        return itemPropertyStringId;
    }

    public void setItemPropertyStringId(LongFilter itemPropertyStringId) {
        this.itemPropertyStringId = itemPropertyStringId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(active, that.active) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(manufacturerId, that.manufacturerId) &&
            Objects.equals(itemPropertyBoolId, that.itemPropertyBoolId) &&
            Objects.equals(itemPropertyDoubleId, that.itemPropertyDoubleId) &&
            Objects.equals(itemPropertyFloatId, that.itemPropertyFloatId) &&
            Objects.equals(itemPropertyIntId, that.itemPropertyIntId) &&
            Objects.equals(itemPropertyStringId, that.itemPropertyStringId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gtin,
        cost,
        name,
        active,
        productId,
        manufacturerId,
        itemPropertyBoolId,
        itemPropertyDoubleId,
        itemPropertyFloatId,
        itemPropertyIntId,
        itemPropertyStringId
        );
    }

    @Override
    public String toString() {
        return "ItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gtin != null ? "gtin=" + gtin + ", " : "") +
                (cost != null ? "cost=" + cost + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (manufacturerId != null ? "manufacturerId=" + manufacturerId + ", " : "") +
                (itemPropertyBoolId != null ? "itemPropertyBoolId=" + itemPropertyBoolId + ", " : "") +
                (itemPropertyDoubleId != null ? "itemPropertyDoubleId=" + itemPropertyDoubleId + ", " : "") +
                (itemPropertyFloatId != null ? "itemPropertyFloatId=" + itemPropertyFloatId + ", " : "") +
                (itemPropertyIntId != null ? "itemPropertyIntId=" + itemPropertyIntId + ", " : "") +
                (itemPropertyStringId != null ? "itemPropertyStringId=" + itemPropertyStringId + ", " : "") +
            "}";
    }

}
