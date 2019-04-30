import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPermission } from 'app/shared/model/permission.model';
import { PermissionService } from './permission.service';
import { IPosition } from 'app/shared/model/position.model';
import { PositionService } from 'app/entities/position';

@Component({
    selector: 'jhi-permission-update',
    templateUrl: './permission-update.component.html'
})
export class PermissionUpdateComponent implements OnInit {
    permission: IPermission;
    isSaving: boolean;

    positions: IPosition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected permissionService: PermissionService,
        protected positionService: PositionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ permission }) => {
            this.permission = permission;
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
        if (this.permission.id !== undefined) {
            this.subscribeToSaveResponse(this.permissionService.update(this.permission));
        } else {
            this.subscribeToSaveResponse(this.permissionService.create(this.permission));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermission>>) {
        result.subscribe((res: HttpResponse<IPermission>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
