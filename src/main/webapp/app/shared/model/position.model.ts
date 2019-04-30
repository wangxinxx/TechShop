export interface IPosition {
    id?: number;
    name?: string;
    managerName?: string;
    managerId?: number;
}

export class Position implements IPosition {
    constructor(public id?: number, public name?: string, public managerName?: string, public managerId?: number) {}
}
