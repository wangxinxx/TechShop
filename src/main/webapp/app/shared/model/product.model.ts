import { IProperty } from 'app/shared/model/property.model';

export interface IProduct {
    id?: number;
    name?: string;
    properties?: IProperty[];
    parentName?: string;
    parentId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public properties?: IProperty[],
        public parentName?: string,
        public parentId?: number
    ) {}
}
