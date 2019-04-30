import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPassport } from 'app/shared/model/passport.model';

@Component({
    selector: 'jhi-passport-detail',
    templateUrl: './passport-detail.component.html'
})
export class PassportDetailComponent implements OnInit {
    passport: IPassport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ passport }) => {
            this.passport = passport;
        });
    }

    previousState() {
        window.history.back();
    }
}
