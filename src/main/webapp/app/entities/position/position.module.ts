import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    PositionComponent,
    PositionDetailComponent,
    PositionUpdateComponent,
    PositionDeletePopupComponent,
    PositionDeleteDialogComponent,
    positionRoute,
    positionPopupRoute
} from './';

const ENTITY_STATES = [...positionRoute, ...positionPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PositionComponent,
        PositionDetailComponent,
        PositionUpdateComponent,
        PositionDeleteDialogComponent,
        PositionDeletePopupComponent
    ],
    entryComponents: [PositionComponent, PositionUpdateComponent, PositionDeleteDialogComponent, PositionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopPositionModule {}
