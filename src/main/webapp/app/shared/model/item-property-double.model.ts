export interface IItemPropertyDouble {
    id?: number;
    value?: number;
    propertyName?: string;
    propertyId?: number;
    itemName?: string;
    itemId?: number;
}

export class ItemPropertyDouble implements IItemPropertyDouble {
    constructor(
        public id?: number,
        public value?: number,
        public propertyName?: string,
        public propertyId?: number,
        public itemName?: string,
        public itemId?: number
    ) {}
}
