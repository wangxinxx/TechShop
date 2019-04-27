import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    ItemPropertyStringComponent,
    ItemPropertyStringDetailComponent,
    ItemPropertyStringUpdateComponent,
    ItemPropertyStringDeletePopupComponent,
    ItemPropertyStringDeleteDialogComponent,
    itemPropertyStringRoute,
    itemPropertyStringPopupRoute
} from './';

const ENTITY_STATES = [...itemPropertyStringRoute, ...itemPropertyStringPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemPropertyStringComponent,
        ItemPropertyStringDetailComponent,
        ItemPropertyStringUpdateComponent,
        ItemPropertyStringDeleteDialogComponent,
        ItemPropertyStringDeletePopupComponent
    ],
    entryComponents: [
        ItemPropertyStringComponent,
        ItemPropertyStringUpdateComponent,
        ItemPropertyStringDeleteDialogComponent,
        ItemPropertyStringDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopItemPropertyStringModule {}
