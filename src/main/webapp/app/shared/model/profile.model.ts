import { IPassport } from 'app/shared/model/passport.model';

export interface IProfile {
    id?: number;
    phone?: string;
    positionName?: string;
    positionId?: number;
    passports?: IPassport[];
    userLogin?: string;
    userId?: number;
}

export class Profile implements IProfile {
    constructor(
        public id?: number,
        public phone?: string,
        public positionName?: string,
        public positionId?: number,
        public passports?: IPassport[],
        public userLogin?: string,
        public userId?: number
    ) {}
}
