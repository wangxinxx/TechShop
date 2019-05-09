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
 * Criteria class for the Address entity. This class is used in AddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter zipCode;

    private StringFilter street;

    private StringFilter houseNumber;

    private StringFilter apartmentNumber;

    private LongFilter cityId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(StringFilter houseNumber) {
        this.houseNumber = houseNumber;
    }

    public StringFilter getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(StringFilter apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressCriteria that = (AddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(street, that.street) &&
            Objects.equals(houseNumber, that.houseNumber) &&
            Objects.equals(apartmentNumber, that.apartmentNumber) &&
            Objects.equals(cityId, that.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        zipCode,
        street,
        houseNumber,
        apartmentNumber,
        cityId
        );
    }

    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (houseNumber != null ? "houseNumber=" + houseNumber + ", " : "") +
                (apartmentNumber != null ? "apartmentNumber=" + apartmentNumber + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
            "}";
    }

}
