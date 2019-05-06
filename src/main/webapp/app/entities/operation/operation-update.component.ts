import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from './operation.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-operation-update',
    templateUrl: './operation-update.component.html'
})
export class OperationUpdateComponent implements OnInit {
    operation: IOperation;
    isSaving: boolean;

    profiles: IProfile[];

    items: IItem[];
    createdAtDp: any;
    lastModifiedAtDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected operationService: OperationService,
        protected profileService: ProfileService,
        protected itemService: ItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operation }) => {
            this.operation = operation;
        });
        this.profileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProfile[]>) => response.body)
            )
            .subscribe((res: IProfile[]) => (this.profiles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.itemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItem[]>) => response.body)
            )
            .subscribe((res: IItem[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operation.id !== undefined) {
            this.subscribeToSaveResponse(this.operationService.update(this.operation));
        } else {
            this.subscribeToSaveResponse(this.operationService.create(this.operation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperation>>) {
        result.subscribe((res: HttpResponse<IOperation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
}
