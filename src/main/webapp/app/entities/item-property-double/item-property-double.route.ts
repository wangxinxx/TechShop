import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemPropertyDouble } from 'app/shared/model/item-property-double.model';
import { ItemPropertyDoubleService } from './item-property-double.service';
import { ItemPropertyDoubleComponent } from './item-property-double.component';
import { ItemPropertyDoubleDetailComponent } from './item-property-double-detail.component';
import { ItemPropertyDoubleUpdateComponent } from './item-property-double-update.component';
import { ItemPropertyDoubleDeletePopupComponent } from './item-property-double-delete-dialog.component';
import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';

@Injectable({ providedIn: 'root' })
export class ItemPropertyDoubleResolve implements Resolve<IItemPropertyDouble> {
    constructor(private service: ItemPropertyDoubleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemPropertyDouble> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemPropertyDouble>) => response.ok),
                map((itemPropertyDouble: HttpResponse<ItemPropertyDouble>) => itemPropertyDouble.body)
            );
        }
        return of(new ItemPropertyDouble());
    }
}

export const itemPropertyDoubleRoute: Routes = [
    {
        path: '',
        component: ItemPropertyDoubleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyDoubles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemPropertyDoubleDetailComponent,
        resolve: {
            itemPropertyDouble: ItemPropertyDoubleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyDoubles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemPropertyDoubleUpdateComponent,
        resolve: {
            itemPropertyDouble: ItemPropertyDoubleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyDoubles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemPropertyDoubleUpdateComponent,
        resolve: {
            itemPropertyDouble: ItemPropertyDoubleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyDoubles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPropertyDoublePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemPropertyDoubleDeletePopupComponent,
        resolve: {
            itemPropertyDouble: ItemPropertyDoubleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyDoubles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
