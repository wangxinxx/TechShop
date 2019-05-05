import { IPassport } from 'app/shared/model/passport.model';

export interface IProfile {
    id?: number;
    phone?: string;
    address?: string;
    zipCode?: string;
    active?: boolean;
    positionName?: string;
    positionId?: number;
    passports?: IPassport[];
    userLogin?: string;
    userId?: number;
    cityName?: string;
    cityId?: number;
}

export class Profile implements IProfile {
    constructor(
        public id?: number,
        public phone?: string,
        public address?: string,
        public zipCode?: string,
        public active?: boolean,
        public positionName?: string,
        public positionId?: number,
        public passports?: IPassport[],
        public userLogin?: string,
        public userId?: number,
        public cityName?: string,
        public cityId?: number
    ) {
        this.active = this.active || false;
    }
}
