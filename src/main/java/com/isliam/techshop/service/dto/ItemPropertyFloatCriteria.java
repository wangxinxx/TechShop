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
 * Criteria class for the ItemPropertyFloat entity. This class is used in ItemPropertyFloatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /item-property-floats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemPropertyFloatCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter value;

    private LongFilter itemId;

    private LongFilter propertyId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getValue() {
        return value;
    }

    public void setValue(FloatFilter value) {
        this.value = value;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public LongFilter getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(LongFilter propertyId) {
        this.propertyId = propertyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemPropertyFloatCriteria that = (ItemPropertyFloatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        itemId,
        propertyId
        );
    }

    @Override
    public String toString() {
        return "ItemPropertyFloatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
                (propertyId != null ? "propertyId=" + propertyId + ", " : "") +
            "}";
    }

}
