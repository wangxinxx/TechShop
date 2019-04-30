package com.isliam.techshop.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Permission entity.
 */
public class PermissionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PermissionDTO permissionDTO = (PermissionDTO) o;
        if (permissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), permissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
