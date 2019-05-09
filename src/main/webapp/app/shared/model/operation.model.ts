import { Moment } from 'moment';

export const enum OperationType {
    SELL = 'SELL',
    RETURN = 'RETURN'
}

export const enum OperationState {
    SUCCESS = 'SUCCESS',
    FAILURE = 'FAILURE',
    PENDING = 'PENDING'
}

export interface IOperation {
    id?: number;
    type?: OperationType;
    state?: OperationState;
    description?: string;
    orderDate?: Moment;
    approveDate?: Moment;
    deliveryDate?: Moment;
    customerId?: number;
    sellerId?: number;
    curierId?: number;
    itemName?: string;
    itemId?: number;
    addressStreet?: string;
    addressId?: number;
}

export class Operation implements IOperation {
    constructor(
        public id?: number,
        public type?: OperationType,
        public state?: OperationState,
        public description?: string,
        public orderDate?: Moment,
        public approveDate?: Moment,
        public deliveryDate?: Moment,
        public customerId?: number,
        public sellerId?: number,
        public curierId?: number,
        public itemName?: string,
        public itemId?: number,
        public addressStreet?: string,
        public addressId?: number
    ) {}
}
