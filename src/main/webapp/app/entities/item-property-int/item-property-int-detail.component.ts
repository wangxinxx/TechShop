import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';

@Component({
    selector: 'jhi-item-property-int-detail',
    templateUrl: './item-property-int-detail.component.html'
})
export class ItemPropertyIntDetailComponent implements OnInit {
    itemPropertyInt: IItemPropertyInt;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyInt }) => {
            this.itemPropertyInt = itemPropertyInt;
        });
    }

    previousState() {
        window.history.back();
    }
}
