export interface IItemPropertyString {
    id?: number;
    value?: string;
    itemId?: number;
    propertyName?: string;
    propertyId?: number;
}

export class ItemPropertyString implements IItemPropertyString {
    constructor(
        public id?: number,
        public value?: string,
        public itemId?: number,
        public propertyName?: string,
        public propertyId?: number
    ) {}
}
