import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';
import { ItemPropertyDoubleService } from './item-property-double.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-item-property-double-update',
    templateUrl: './item-property-double-update.component.html'
})
export class ItemPropertyDoubleUpdateComponent implements OnInit {
    itemPropertyDouble: IItemPropertyDouble;
    isSaving: boolean;

    properties: IProperty[];

    items: IItem[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemPropertyDoubleService: ItemPropertyDoubleService,
        protected propertyService: PropertyService,
        protected itemService: ItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemPropertyDouble }) => {
            this.itemPropertyDouble = itemPropertyDouble;
        });
        this.propertyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProperty[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProperty[]>) => response.body)
            )
            .subscribe((res: IProperty[]) => (this.properties = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.itemPropertyDouble.id !== undefined) {
            this.subscribeToSaveResponse(this.itemPropertyDoubleService.update(this.itemPropertyDouble));
        } else {
            this.subscribeToSaveResponse(this.itemPropertyDoubleService.create(this.itemPropertyDouble));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemPropertyDouble>>) {
        result.subscribe((res: HttpResponse<IItemPropertyDouble>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPropertyById(index: number, item: IProperty) {
        return item.id;
    }

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
}
