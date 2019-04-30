import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    PassportComponent,
    PassportDetailComponent,
    PassportUpdateComponent,
    PassportDeletePopupComponent,
    PassportDeleteDialogComponent,
    passportRoute,
    passportPopupRoute
} from './';

const ENTITY_STATES = [...passportRoute, ...passportPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PassportComponent,
        PassportDetailComponent,
        PassportUpdateComponent,
        PassportDeleteDialogComponent,
        PassportDeletePopupComponent
    ],
    entryComponents: [PassportComponent, PassportUpdateComponent, PassportDeleteDialogComponent, PassportDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopPassportModule {}
