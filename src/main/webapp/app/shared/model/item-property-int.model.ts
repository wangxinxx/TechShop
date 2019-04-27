export interface IItemPropertyInt {
    id?: number;
    value?: number;
    itemName?: string;
    itemId?: number;
    propertyName?: string;
    propertyId?: number;
}

export class ItemPropertyInt implements IItemPropertyInt {
    constructor(
        public id?: number,
        public value?: number,
        public itemName?: string,
        public itemId?: number,
        public propertyName?: string,
        public propertyId?: number
    ) {}
}
