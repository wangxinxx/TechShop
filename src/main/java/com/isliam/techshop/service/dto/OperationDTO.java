package com.isliam.techshop.service.dto;
import java.time.LocalDate;
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

    private LocalDate orderDate;

    private LocalDate approveDate;

    private LocalDate deliveryDate;


    private Long customerId;

    private Long sellerId;

    private Long curierId;

    private Long itemId;

    private String itemName;

    private Long addressId;

    private String addressStreet;

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
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
            ", orderDate='" + getOrderDate() + "'" +
            ", approveDate='" + getApproveDate() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", customer=" + getCustomerId() +
            ", seller=" + getSellerId() +
            ", curier=" + getCurierId() +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            ", address=" + getAddressId() +
            ", address='" + getAddressStreet() + "'" +
            "}";
    }
}
