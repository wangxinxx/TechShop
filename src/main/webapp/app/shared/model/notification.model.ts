export interface INotification {
    id?: number;
    message?: string;
    viewed?: boolean;
    profileId?: number;
}

export class Notification implements INotification {
    constructor(public id?: number, public message?: string, public viewed?: boolean, public profileId?: number) {
        this.viewed = this.viewed || false;
    }
}
