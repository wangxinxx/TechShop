import { Moment } from 'moment';

export interface IPassport {
    id?: number;
    firstName?: string;
    lastName?: string;
    patronymic?: string;
    dob?: Moment;
    serialNumber?: string;
    taxId?: string;
    active?: boolean;
    profileId?: number;
}

export class Passport implements IPassport {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public patronymic?: string,
        public dob?: Moment,
        public serialNumber?: string,
        public taxId?: string,
        public active?: boolean,
        public profileId?: number
    ) {
        this.active = this.active || false;
    }
}
