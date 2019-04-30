import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechShopSharedModule } from 'app/shared';
import {
    PermissionComponent,
    PermissionDetailComponent,
    PermissionUpdateComponent,
    PermissionDeletePopupComponent,
    PermissionDeleteDialogComponent,
    permissionRoute,
    permissionPopupRoute
} from './';

const ENTITY_STATES = [...permissionRoute, ...permissionPopupRoute];

@NgModule({
    imports: [TechShopSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PermissionComponent,
        PermissionDetailComponent,
        PermissionUpdateComponent,
        PermissionDeleteDialogComponent,
        PermissionDeletePopupComponent
    ],
    entryComponents: [PermissionComponent, PermissionUpdateComponent, PermissionDeleteDialogComponent, PermissionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechShopPermissionModule {}
