import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPassport } from 'app/shared/model/passport.model';
import { PassportService } from './passport.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

@Component({
    selector: 'jhi-passport-update',
    templateUrl: './passport-update.component.html'
})
export class PassportUpdateComponent implements OnInit {
    passport: IPassport;
    isSaving: boolean;

    profiles: IProfile[];
    dobDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected passportService: PassportService,
        protected profileService: ProfileService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ passport }) => {
            this.passport = passport;
        });
        this.profileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProfile[]>) => response.body)
            )
            .subscribe((res: IProfile[]) => (this.profiles = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProfileById(index: number, item: IProfile) {
        return item.id;
    }
}
