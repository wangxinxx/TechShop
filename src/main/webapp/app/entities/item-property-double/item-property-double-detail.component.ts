import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';

@Component({
    selector: 'jhi-item-property-double-detail',
    templateUrl: './item-property-double-detail.component.html'
})
export class ItemPropertyDoubleDetailComponent implements OnInit {
    itemPropertyDouble: IItemPropertyDouble;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyDouble }) => {
            this.itemPropertyDouble = itemPropertyDouble;
        });
    }

    previousState() {
        window.history.back();
    }
}
