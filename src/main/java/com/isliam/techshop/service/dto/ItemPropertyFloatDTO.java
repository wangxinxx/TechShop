package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemPropertyFloat entity.
 */
public class ItemPropertyFloatDTO implements Serializable {

    private Long id;

    private Float value;


    private Long itemId;

    private String itemName;

    private Long propertyId;

    private String propertyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPropertyFloatDTO itemPropertyFloatDTO = (ItemPropertyFloatDTO) o;
        if (itemPropertyFloatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyFloatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyFloatDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            ", property=" + getPropertyId() +
            ", property='" + getPropertyName() + "'" +
            "}";
    }
}
