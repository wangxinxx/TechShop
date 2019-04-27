export interface IItemPropertyFloat {
    id?: number;
    value?: number;
    itemName?: string;
    itemId?: number;
    propertyName?: string;
    propertyId?: number;
}

export class ItemPropertyFloat implements IItemPropertyFloat {
    constructor(
        public id?: number,
        public value?: number,
        public itemName?: string,
        public itemId?: number,
        public propertyName?: string,
        public propertyId?: number
    ) {}
}
