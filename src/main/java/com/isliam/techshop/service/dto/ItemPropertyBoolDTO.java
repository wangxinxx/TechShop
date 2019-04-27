package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemPropertyBool entity.
 */
public class ItemPropertyBoolDTO implements Serializable {

    private Long id;

    private Boolean value;


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

    public Boolean isValue() {
        return value;
    }

    public void setValue(Boolean value) {
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

        ItemPropertyBoolDTO itemPropertyBoolDTO = (ItemPropertyBoolDTO) o;
        if (itemPropertyBoolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyBoolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyBoolDTO{" +
            "id=" + getId() +
            ", value='" + isValue() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            ", property=" + getPropertyId() +
            ", property='" + getPropertyName() + "'" +
            "}";
    }
}
