export interface IAddress {
    id?: number;
    zipCode?: string;
    street?: string;
    houseNumber?: string;
    apartmentNumber?: string;
    cityName?: string;
    cityId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public zipCode?: string,
        public street?: string,
        public houseNumber?: string,
        public apartmentNumber?: string,
        public cityName?: string,
        public cityId?: number
    ) {}
}
