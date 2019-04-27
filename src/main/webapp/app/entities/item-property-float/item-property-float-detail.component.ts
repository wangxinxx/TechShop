import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';

@Component({
    selector: 'jhi-item-property-float-detail',
    templateUrl: './item-property-float-detail.component.html'
})
export class ItemPropertyFloatDetailComponent implements OnInit {
    itemPropertyFloat: IItemPropertyFloat;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyFloat }) => {
            this.itemPropertyFloat = itemPropertyFloat;
        });
    }

    previousState() {
        window.history.back();
    }
}
