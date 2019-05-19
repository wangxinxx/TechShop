import { IItem } from 'app/shared/model/item.model';

export interface IManufacturer {
    id?: number;
    name?: string;
    items?: IItem[];
}

export class Manufacturer implements IManufacturer {
    constructor(public id?: number, public name?: string, public items?: IItem[]) {}
}
