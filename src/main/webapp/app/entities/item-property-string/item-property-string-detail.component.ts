import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPropertyString } from 'app/shared/model/item-property-string.model';

@Component({
    selector: 'jhi-item-property-string-detail',
    templateUrl: './item-property-string-detail.component.html'
})
export class ItemPropertyStringDetailComponent implements OnInit {
    itemPropertyString: IItemPropertyString;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyString }) => {
            this.itemPropertyString = itemPropertyString;
        });
    }

    previousState() {
        window.history.back();
    }
}
