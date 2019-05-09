export interface ICity {
    id?: number;
    name?: string;
    regionName?: string;
    regionId?: number;
}

export class City implements ICity {
    constructor(public id?: number, public name?: string, public regionName?: string, public regionId?: number) {}
}
