import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    ItemPropertyIntComponent,
    ItemPropertyIntDetailComponent,
    ItemPropertyIntUpdateComponent,
    ItemPropertyIntDeletePopupComponent,
    ItemPropertyIntDeleteDialogComponent,
    itemPropertyIntRoute,
    itemPropertyIntPopupRoute
} from './';

const ENTITY_STATES = [...itemPropertyIntRoute, ...itemPropertyIntPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemPropertyIntComponent,
        ItemPropertyIntDetailComponent,
        ItemPropertyIntUpdateComponent,
        ItemPropertyIntDeleteDialogComponent,
        ItemPropertyIntDeletePopupComponent
    ],
    entryComponents: [
        ItemPropertyIntComponent,
        ItemPropertyIntUpdateComponent,
        ItemPropertyIntDeleteDialogComponent,
        ItemPropertyIntDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopItemPropertyIntModule {}
