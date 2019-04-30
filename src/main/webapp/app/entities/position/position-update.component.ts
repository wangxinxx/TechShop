import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPosition } from 'app/shared/model/position.model';
import { PositionService } from './position.service';
import { IPermission } from 'app/shared/model/permission.model';
import { PermissionService } from 'app/entities/permission';

@Component({
    selector: 'jhi-position-update',
    templateUrl: './position-update.component.html'
})
export class PositionUpdateComponent implements OnInit {
    position: IPosition;
    isSaving: boolean;

    positions: IPosition[];

    permissions: IPermission[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected positionService: PositionService,
        protected permissionService: PermissionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ position }) => {
            this.position = position;
        });
        this.positionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPosition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPosition[]>) => response.body)
            )
            .subscribe((res: IPosition[]) => (this.positions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.permissionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPermission[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPermission[]>) => response.body)
            )
            .subscribe((res: IPermission[]) => (this.permissions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.position.id !== undefined) {
            this.subscribeToSaveResponse(this.positionService.update(this.position));
        } else {
            this.subscribeToSaveResponse(this.positionService.create(this.position));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosition>>) {
        result.subscribe((res: HttpResponse<IPosition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPermissionById(index: number, item: IPermission) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
