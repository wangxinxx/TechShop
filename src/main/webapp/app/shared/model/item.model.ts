export interface IItem {
    id?: number;
    gtin?: string;
    barcode?: any;
    cost?: number;
    name?: string;
    active?: boolean;
    productName?: string;
    productId?: number;
}

export class Item implements IItem {
    constructor(
        public id?: number,
        public gtin?: string,
        public barcode?: any,
        public cost?: number,
        public name?: string,
        public active?: boolean,
        public productName?: string,
        public productId?: number
    ) {
        this.active = this.active || false;
    }
}
