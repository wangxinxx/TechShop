import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPassport } from 'app/shared/model/passport.model';
import { PassportService } from './passport.service';

@Component({
    selector: 'jhi-passport-update',
    templateUrl: './passport-update.component.html'
})
export class PassportUpdateComponent implements OnInit {
    passport: IPassport;
    isSaving: boolean;
    dobDp: any;

    constructor(protected passportService: PassportService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ passport }) => {
            this.passport = passport;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.passport.id !== undefined) {
            this.subscribeToSaveResponse(this.passportService.update(this.passport));
        } else {
            this.subscribeToSaveResponse(this.passportService.create(this.passport));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPassport>>) {
        result.subscribe((res: HttpResponse<IPassport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
