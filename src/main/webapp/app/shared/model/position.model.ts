import { IPermission } from 'app/shared/model/permission.model';

export interface IPosition {
    id?: number;
    name?: string;
    managerName?: string;
    managerId?: number;
    permissions?: IPermission[];
}

export class Position implements IPosition {
    constructor(
        public id?: number,
        public name?: string,
        public managerName?: string,
        public managerId?: number,
        public permissions?: IPermission[]
    ) {}
}
