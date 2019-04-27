import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    ItemPropertyFloatComponent,
    ItemPropertyFloatDetailComponent,
    ItemPropertyFloatUpdateComponent,
    ItemPropertyFloatDeletePopupComponent,
    ItemPropertyFloatDeleteDialogComponent,
    itemPropertyFloatRoute,
    itemPropertyFloatPopupRoute
} from './';

const ENTITY_STATES = [...itemPropertyFloatRoute, ...itemPropertyFloatPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemPropertyFloatComponent,
        ItemPropertyFloatDetailComponent,
        ItemPropertyFloatUpdateComponent,
        ItemPropertyFloatDeleteDialogComponent,
        ItemPropertyFloatDeletePopupComponent
    ],
    entryComponents: [
        ItemPropertyFloatComponent,
        ItemPropertyFloatUpdateComponent,
        ItemPropertyFloatDeleteDialogComponent,
        ItemPropertyFloatDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopItemPropertyFloatModule {}
