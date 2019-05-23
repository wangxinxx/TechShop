import { IItemPropertyBool } from 'app/shared/model/item-property-bool.model';
import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';
import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';
import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';
import { IItemPropertyString } from 'app/shared/model/item-property-string.model';

export interface IItem {
    id?: number;
    gtin?: string;
    barcode?: any;
    cost?: number;
    name?: string;
    active?: boolean;
    productName?: string;
    productId?: number;
    manufacturerName?: string;
    manufacturerId?: number;
    itemPropertyBools?: IItemPropertyBool[];
    itemPropertyDoubles?: IItemPropertyDouble[];
    itemPropertyFloats?: IItemPropertyFloat[];
    itemPropertyInts?: IItemPropertyInt[];
    itemPropertyStrings?: IItemPropertyString[];
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
        public productId?: number,
        public manufacturerName?: string,
        public manufacturerId?: number,
        public itemPropertyBools?: IItemPropertyBool[],
        public itemPropertyDoubles?: IItemPropertyDouble[],
        public itemPropertyFloats?: IItemPropertyFloat[],
        public itemPropertyInts?: IItemPropertyInt[],
        public itemPropertyStrings?: IItemPropertyString[],
        public properties?: Map<String, String>
    ) {
        this.active = this.active || false;
    }
}
