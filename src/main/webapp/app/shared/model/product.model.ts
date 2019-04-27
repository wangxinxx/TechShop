import { IProperty } from 'app/shared/model/property.model';

export interface IProduct {
    id?: number;
    name?: string;
    properties?: IProperty[];
}

export class Product implements IProduct {
    constructor(public id?: number, public name?: string, public properties?: IProperty[]) {}
}
