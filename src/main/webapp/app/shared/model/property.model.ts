import { IProduct } from 'app/shared/model/product.model';

export const enum ValueType {
    STRING = 'STRING',
    FLOAT = 'FLOAT',
    DATE = 'DATE',
    INTEGER = 'INTEGER',
    BOOLEAN = 'BOOLEAN',
    DOUBLE = 'DOUBLE'
}

export interface IProperty {
    id?: number;
    name?: string;
    valueType?: ValueType;
    products?: IProduct[];
}

export class Property implements IProperty {
    constructor(public id?: number, public name?: string, public valueType?: ValueType, public products?: IProduct[]) {}
}
