package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemPropertyDouble entity.
 */
public class ItemPropertyDoubleDTO implements Serializable {

    private Long id;

    private Double value;


    private Long propertyId;

    private String propertyName;

    private Long itemId;

    private String itemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPropertyDoubleDTO itemPropertyDoubleDTO = (ItemPropertyDoubleDTO) o;
        if (itemPropertyDoubleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyDoubleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyDoubleDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", property=" + getPropertyId() +
            ", property='" + getPropertyName() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            "}";
    }
}
