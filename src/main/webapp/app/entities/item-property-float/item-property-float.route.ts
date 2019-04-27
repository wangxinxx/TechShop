import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemPropertyFloat } from 'app/shared/model/item-property-float.model';
import { ItemPropertyFloatService } from './item-property-float.service';
import { ItemPropertyFloatComponent } from './item-property-float.component';
import { ItemPropertyFloatDetailComponent } from './item-property-float-detail.component';
import { ItemPropertyFloatUpdateComponent } from './item-property-float-update.component';
import { ItemPropertyFloatDeletePopupComponent } from './item-property-float-delete-dialog.component';
import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';

@Injectable({ providedIn: 'root' })
export class ItemPropertyFloatResolve implements Resolve<IItemPropertyFloat> {
    constructor(private service: ItemPropertyFloatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemPropertyFloat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemPropertyFloat>) => response.ok),
                map((itemPropertyFloat: HttpResponse<ItemPropertyFloat>) => itemPropertyFloat.body)
            );
        }
        return of(new ItemPropertyFloat());
    }
}

export const itemPropertyFloatRoute: Routes = [
    {
        path: '',
        component: ItemPropertyFloatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyFloats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemPropertyFloatDetailComponent,
        resolve: {
            itemPropertyFloat: ItemPropertyFloatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyFloats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemPropertyFloatUpdateComponent,
        resolve: {
            itemPropertyFloat: ItemPropertyFloatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyFloats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemPropertyFloatUpdateComponent,
        resolve: {
            itemPropertyFloat: ItemPropertyFloatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyFloats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPropertyFloatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemPropertyFloatDeletePopupComponent,
        resolve: {
            itemPropertyFloat: ItemPropertyFloatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyFloats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
