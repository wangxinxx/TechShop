import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';
import { ItemPropertyFloatService } from './item-property-float.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';

@Component({
    selector: 'jhi-item-property-float-update',
    templateUrl: './item-property-float-update.component.html'
})
export class ItemPropertyFloatUpdateComponent implements OnInit {
    itemPropertyFloat: IItemPropertyFloat;
    isSaving: boolean;

    items: IItem[];

    properties: IProperty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemPropertyFloatService: ItemPropertyFloatService,
        protected itemService: ItemService,
        protected propertyService: PropertyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemPropertyFloat }) => {
            this.itemPropertyFloat = itemPropertyFloat;
        });
        this.itemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItem[]>) => response.body)
            )
            .subscribe((res: IItem[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.propertyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProperty[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProperty[]>) => response.body)
            )
            .subscribe((res: IProperty[]) => (this.properties = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.itemPropertyFloat.id !== undefined) {
            this.subscribeToSaveResponse(this.itemPropertyFloatService.update(this.itemPropertyFloat));
        } else {
            this.subscribeToSaveResponse(this.itemPropertyFloatService.create(this.itemPropertyFloat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemPropertyFloat>>) {
        result.subscribe((res: HttpResponse<IItemPropertyFloat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackItemById(index: number, item: IItem) {
        return item.id;
    }

    trackPropertyById(index: number, item: IProperty) {
        return item.id;
    }
}
