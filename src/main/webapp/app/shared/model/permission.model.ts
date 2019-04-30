import { IPosition } from 'app/shared/model/position.model';

export interface IPermission {
    id?: number;
    name?: string;
    positions?: IPosition[];
}

export class Permission implements IPermission {
    constructor(public id?: number, public name?: string, public positions?: IPosition[]) {}
}
