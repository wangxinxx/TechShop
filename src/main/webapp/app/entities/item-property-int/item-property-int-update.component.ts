import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';
import { ItemPropertyIntService } from './item-property-int.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';

@Component({
    selector: 'jhi-item-property-int-update',
    templateUrl: './item-property-int-update.component.html'
})
export class ItemPropertyIntUpdateComponent implements OnInit {
    itemPropertyInt: IItemPropertyInt;
    isSaving: boolean;

    items: IItem[];

    properties: IProperty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemPropertyIntService: ItemPropertyIntService,
        protected itemService: ItemService,
        protected propertyService: PropertyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemPropertyInt }) => {
            this.itemPropertyInt = itemPropertyInt;
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
        if (this.itemPropertyInt.id !== undefined) {
            this.subscribeToSaveResponse(this.itemPropertyIntService.update(this.itemPropertyInt));
        } else {
            this.subscribeToSaveResponse(this.itemPropertyIntService.create(this.itemPropertyInt));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemPropertyInt>>) {
        result.subscribe((res: HttpResponse<IItemPropertyInt>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
