import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IPosition } from 'app/shared/model/position.model';
import { PositionService } from 'app/entities/position';

@Component({
    selector: 'jhi-profile-update',
    templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
    profile: IProfile;
    isSaving: boolean;

    positions: IPosition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected profileService: ProfileService,
        protected positionService: PositionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ profile }) => {
            this.profile = profile;
        });
        this.positionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPosition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPosition[]>) => response.body)
            )
            .subscribe((res: IPosition[]) => (this.positions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.profile.id !== undefined) {
            this.subscribeToSaveResponse(this.profileService.update(this.profile));
        } else {
            this.subscribeToSaveResponse(this.profileService.create(this.profile));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>) {
        result.subscribe((res: HttpResponse<IProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPositionById(index: number, item: IPosition) {
        return item.id;
    }
}
