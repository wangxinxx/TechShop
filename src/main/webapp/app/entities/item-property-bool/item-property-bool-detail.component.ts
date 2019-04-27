import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPropertyBool } from 'app/shared/model/item-property-bool.model';

@Component({
    selector: 'jhi-item-property-bool-detail',
    templateUrl: './item-property-bool-detail.component.html'
})
export class ItemPropertyBoolDetailComponent implements OnInit {
    itemPropertyBool: IItemPropertyBool;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyBool }) => {
            this.itemPropertyBool = itemPropertyBool;
        });
    }

    previousState() {
        window.history.back();
    }
}
