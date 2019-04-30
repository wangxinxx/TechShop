import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Passport } from 'app/shared/model/passport.model';
import { PassportService } from './passport.service';
import { PassportComponent } from './passport.component';
import { PassportDetailComponent } from './passport-detail.component';
import { PassportUpdateComponent } from './passport-update.component';
import { PassportDeletePopupComponent } from './passport-delete-dialog.component';
import { IPassport } from 'app/shared/model/passport.model';

@Injectable({ providedIn: 'root' })
export class PassportResolve implements Resolve<IPassport> {
    constructor(private service: PassportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPassport> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Passport>) => response.ok),
                map((passport: HttpResponse<Passport>) => passport.body)
            );
        }
        return of(new Passport());
    }
}

export const passportRoute: Routes = [
    {
        path: '',
        component: PassportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PassportDetailComponent,
        resolve: {
            passport: PassportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PassportUpdateComponent,
        resolve: {
            passport: PassportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PassportUpdateComponent,
        resolve: {
            passport: PassportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const passportPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PassportDeletePopupComponent,
        resolve: {
            passport: PassportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
