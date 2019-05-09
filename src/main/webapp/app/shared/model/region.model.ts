export interface IRegion {
    id?: number;
    name?: string;
    countryName?: string;
    countryId?: number;
}

export class Region implements IRegion {
    constructor(public id?: number, public name?: string, public countryName?: string, public countryId?: number) {}
}
