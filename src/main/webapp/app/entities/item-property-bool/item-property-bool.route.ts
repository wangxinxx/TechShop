import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemPropertyBool } from 'app/shared/model/item-property-bool.model';
import { ItemPropertyBoolService } from './item-property-bool.service';
import { ItemPropertyBoolComponent } from './item-property-bool.component';
import { ItemPropertyBoolDetailComponent } from './item-property-bool-detail.component';
import { ItemPropertyBoolUpdateComponent } from './item-property-bool-update.component';
import { ItemPropertyBoolDeletePopupComponent } from './item-property-bool-delete-dialog.component';
import { IItemPropertyBool } from 'app/shared/model/item-property-bool.model';

@Injectable({ providedIn: 'root' })
export class ItemPropertyBoolResolve implements Resolve<IItemPropertyBool> {
    constructor(private service: ItemPropertyBoolService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemPropertyBool> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemPropertyBool>) => response.ok),
                map((itemPropertyBool: HttpResponse<ItemPropertyBool>) => itemPropertyBool.body)
            );
        }
        return of(new ItemPropertyBool());
    }
}

export const itemPropertyBoolRoute: Routes = [
    {
        path: '',
        component: ItemPropertyBoolComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyBools'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemPropertyBoolDetailComponent,
        resolve: {
            itemPropertyBool: ItemPropertyBoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyBools'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemPropertyBoolUpdateComponent,
        resolve: {
            itemPropertyBool: ItemPropertyBoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyBools'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemPropertyBoolUpdateComponent,
        resolve: {
            itemPropertyBool: ItemPropertyBoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyBools'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPropertyBoolPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemPropertyBoolDeletePopupComponent,
        resolve: {
            itemPropertyBool: ItemPropertyBoolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyBools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
