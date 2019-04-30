import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPermission } from 'app/shared/model/permission.model';
import { PermissionService } from './permission.service';

@Component({
    selector: 'jhi-permission-update',
    templateUrl: './permission-update.component.html'
})
export class PermissionUpdateComponent implements OnInit {
    permission: IPermission;
    isSaving: boolean;

    constructor(protected permissionService: PermissionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ permission }) => {
            this.permission = permission;
        });
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
}
