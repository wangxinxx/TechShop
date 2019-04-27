import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemPropertyInt } from 'app/shared/model/item-property-int.model';
import { ItemPropertyIntService } from './item-property-int.service';
import { ItemPropertyIntComponent } from './item-property-int.component';
import { ItemPropertyIntDetailComponent } from './item-property-int-detail.component';
import { ItemPropertyIntUpdateComponent } from './item-property-int-update.component';
import { ItemPropertyIntDeletePopupComponent } from './item-property-int-delete-dialog.component';
import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';

@Injectable({ providedIn: 'root' })
export class ItemPropertyIntResolve implements Resolve<IItemPropertyInt> {
    constructor(private service: ItemPropertyIntService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemPropertyInt> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemPropertyInt>) => response.ok),
                map((itemPropertyInt: HttpResponse<ItemPropertyInt>) => itemPropertyInt.body)
            );
        }
        return of(new ItemPropertyInt());
    }
}

export const itemPropertyIntRoute: Routes = [
    {
        path: '',
        component: ItemPropertyIntComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyInts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemPropertyIntDetailComponent,
        resolve: {
            itemPropertyInt: ItemPropertyIntResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyInts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemPropertyIntUpdateComponent,
        resolve: {
            itemPropertyInt: ItemPropertyIntResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyInts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemPropertyIntUpdateComponent,
        resolve: {
            itemPropertyInt: ItemPropertyIntResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyInts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPropertyIntPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemPropertyIntDeletePopupComponent,
        resolve: {
            itemPropertyInt: ItemPropertyIntResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyInts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
