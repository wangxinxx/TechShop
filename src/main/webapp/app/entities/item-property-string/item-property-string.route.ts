import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemPropertyString } from 'app/shared/model/item-property-string.model';
import { ItemPropertyStringService } from './item-property-string.service';
import { ItemPropertyStringComponent } from './item-property-string.component';
import { ItemPropertyStringDetailComponent } from './item-property-string-detail.component';
import { ItemPropertyStringUpdateComponent } from './item-property-string-update.component';
import { ItemPropertyStringDeletePopupComponent } from './item-property-string-delete-dialog.component';
import { IItemPropertyString } from 'app/shared/model/item-property-string.model';

@Injectable({ providedIn: 'root' })
export class ItemPropertyStringResolve implements Resolve<IItemPropertyString> {
    constructor(private service: ItemPropertyStringService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemPropertyString> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemPropertyString>) => response.ok),
                map((itemPropertyString: HttpResponse<ItemPropertyString>) => itemPropertyString.body)
            );
        }
        return of(new ItemPropertyString());
    }
}

export const itemPropertyStringRoute: Routes = [
    {
        path: '',
        component: ItemPropertyStringComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyStrings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemPropertyStringDetailComponent,
        resolve: {
            itemPropertyString: ItemPropertyStringResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyStrings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemPropertyStringUpdateComponent,
        resolve: {
            itemPropertyString: ItemPropertyStringResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyStrings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemPropertyStringUpdateComponent,
        resolve: {
            itemPropertyString: ItemPropertyStringResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyStrings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPropertyStringPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemPropertyStringDeletePopupComponent,
        resolve: {
            itemPropertyString: ItemPropertyStringResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPropertyStrings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
