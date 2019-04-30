export interface IProfile {
    id?: number;
    phone?: string;
}

export class Profile implements IProfile {
    constructor(public id?: number, public phone?: string) {}
}
