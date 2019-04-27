export const enum ItemStatus {
    SAVED = 'SAVED',
    IN_SHOP = 'IN_SHOP',
    IN_STOCK = 'IN_STOCK',
    UNAVAILABLE = 'UNAVAILABLE'
}

export interface IItem {
    id?: number;
    gtin?: string;
    barcode?: any;
    cost?: number;
    status?: ItemStatus;
    productName?: string;
    productId?: number;
}

export class Item implements IItem {
    constructor(
        public id?: number,
        public gtin?: string,
        public barcode?: any,
        public cost?: number,
        public status?: ItemStatus,
        public productName?: string,
        public productId?: number
    ) {}
}
