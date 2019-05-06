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
    createdAt?: Moment;
    lastModifiedAt?: Moment;
    customerId?: number;
    sellerId?: number;
    curierId?: number;
    itemName?: string;
    itemId?: number;
}

export class Operation implements IOperation {
    constructor(
        public id?: number,
        public type?: OperationType,
        public state?: OperationState,
        public description?: string,
        public createdAt?: Moment,
        public lastModifiedAt?: Moment,
        public customerId?: number,
        public sellerId?: number,
        public curierId?: number,
        public itemName?: string,
        public itemId?: number
    ) {}
}
