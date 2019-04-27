package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemPropertyString entity.
 */
public class ItemPropertyStringDTO implements Serializable {

    private Long id;

    private String value;


    private Long itemId;

    private Long propertyId;

    private String propertyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

        ItemPropertyStringDTO itemPropertyStringDTO = (ItemPropertyStringDTO) o;
        if (itemPropertyStringDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPropertyStringDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPropertyStringDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", item=" + getItemId() +
            ", property=" + getPropertyId() +
            ", property='" + getPropertyName() + "'" +
            "}";
    }
}
