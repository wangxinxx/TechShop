import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    ItemPropertyDoubleComponent,
    ItemPropertyDoubleDetailComponent,
    ItemPropertyDoubleUpdateComponent,
    ItemPropertyDoubleDeletePopupComponent,
    ItemPropertyDoubleDeleteDialogComponent,
    itemPropertyDoubleRoute,
    itemPropertyDoublePopupRoute
} from './';

const ENTITY_STATES = [...itemPropertyDoubleRoute, ...itemPropertyDoublePopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemPropertyDoubleComponent,
        ItemPropertyDoubleDetailComponent,
        ItemPropertyDoubleUpdateComponent,
        ItemPropertyDoubleDeleteDialogComponent,
        ItemPropertyDoubleDeletePopupComponent
    ],
    entryComponents: [
        ItemPropertyDoubleComponent,
        ItemPropertyDoubleUpdateComponent,
        ItemPropertyDoubleDeleteDialogComponent,
        ItemPropertyDoubleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopItemPropertyDoubleModule {}
