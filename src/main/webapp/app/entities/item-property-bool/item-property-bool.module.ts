import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    ItemPropertyBoolComponent,
    ItemPropertyBoolDetailComponent,
    ItemPropertyBoolUpdateComponent,
    ItemPropertyBoolDeletePopupComponent,
    ItemPropertyBoolDeleteDialogComponent,
    itemPropertyBoolRoute,
    itemPropertyBoolPopupRoute
} from './';

const ENTITY_STATES = [...itemPropertyBoolRoute, ...itemPropertyBoolPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemPropertyBoolComponent,
        ItemPropertyBoolDetailComponent,
        ItemPropertyBoolUpdateComponent,
        ItemPropertyBoolDeleteDialogComponent,
        ItemPropertyBoolDeletePopupComponent
    ],
    entryComponents: [
        ItemPropertyBoolComponent,
        ItemPropertyBoolUpdateComponent,
        ItemPropertyBoolDeleteDialogComponent,
        ItemPropertyBoolDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopItemPropertyBoolModule {}
