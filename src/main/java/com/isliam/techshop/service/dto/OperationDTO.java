package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.isliam.techshop.domain.enumeration.OperationType;
import com.isliam.techshop.domain.enumeration.OperationState;

/**
 * A DTO for the Operation entity.
 */
public class OperationDTO implements Serializable {

    private Long id;

    @NotNull
    private OperationType type;

    @NotNull
    private OperationState state;

    private String description;


    private Long customerId;

    private Long sellerId;

    private Long curierId;

    private Long itemId;

    private String itemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public OperationState getState() {
        return state;
    }

    public void setState(OperationState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long profileId) {
        this.customerId = profileId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long profileId) {
        this.sellerId = profileId;
    }

    public Long getCurierId() {
        return curierId;
    }

    public void setCurierId(Long profileId) {
        this.curierId = profileId;
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

        OperationDTO operationDTO = (OperationDTO) o;
        if (operationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", description='" + getDescription() + "'" +
            ", customer=" + getCustomerId() +
            ", seller=" + getSellerId() +
            ", curier=" + getCurierId() +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            "}";
    }
}
