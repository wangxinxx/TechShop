export interface IItemPropertyBool {
    id?: number;
    value?: boolean;
    itemName?: string;
    itemId?: number;
    propertyName?: string;
    propertyId?: number;
}

export class ItemPropertyBool implements IItemPropertyBool {
    constructor(
        public id?: number,
        public value?: boolean,
        public itemName?: string,
        public itemId?: number,
        public propertyName?: string,
        public propertyId?: number
    ) {
        this.value = this.value || false;
    }
}
