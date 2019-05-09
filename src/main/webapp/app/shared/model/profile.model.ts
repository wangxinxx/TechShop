import { IPassport } from 'app/shared/model/passport.model';

export interface IProfile {
    id?: number;
    phone?: string;
    active?: boolean;
    positionName?: string;
    positionId?: number;
    passports?: IPassport[];
    userLogin?: string;
    userId?: number;
    addressStreet?: string;
    addressId?: number;
}

export class Profile implements IProfile {
    constructor(
        public id?: number,
        public phone?: string,
        public active?: boolean,
        public positionName?: string,
        public positionId?: number,
        public passports?: IPassport[],
        public userLogin?: string,
        public userId?: number,
        public addressStreet?: string,
        public addressId?: number
    ) {
        this.active = this.active || false;
    }
}
