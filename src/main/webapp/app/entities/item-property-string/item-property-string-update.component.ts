import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IItemPropertyString } from 'app/shared/model/item-property-string.model';
import { ItemPropertyStringService } from './item-property-string.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';

@Component({
    selector: 'jhi-item-property-string-update',
    templateUrl: './item-property-string-update.component.html'
})
export class ItemPropertyStringUpdateComponent implements OnInit {
    itemPropertyString: IItemPropertyString;
    isSaving: boolean;

    items: IItem[];

    properties: IProperty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemPropertyStringService: ItemPropertyStringService,
        protected itemService: ItemService,
        protected propertyService: PropertyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemPropertyString }) => {
            this.itemPropertyString = itemPropertyString;
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
        if (this.itemPropertyString.id !== undefined) {
            this.subscribeToSaveResponse(this.itemPropertyStringService.update(this.itemPropertyString));
        } else {
            this.subscribeToSaveResponse(this.itemPropertyStringService.create(this.itemPropertyString));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemPropertyString>>) {
        result.subscribe((res: HttpResponse<IItemPropertyString>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
