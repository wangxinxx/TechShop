import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { ProfileComponent } from './profile.component';
import { ProfileDetailComponent } from './profile-detail.component';
import { ProfileUpdateComponent } from './profile-update.component';
import { ProfileDeletePopupComponent } from './profile-delete-dialog.component';
import { IProfile } from 'app/shared/model/profile.model';

@Injectable({ providedIn: 'root' })
export class ProfileResolve implements Resolve<IProfile> {
    constructor(private service: ProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Profile>) => response.ok),
                map((profile: HttpResponse<Profile>) => profile.body)
            );
        }
        return of(new Profile());
    }
}

export const profileRoute: Routes = [
    {
        path: '',
        component: ProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProfileDetailComponent,
        resolve: {
            profile: ProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProfileUpdateComponent,
        resolve: {
            profile: ProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProfileUpdateComponent,
        resolve: {
            profile: ProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profilePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProfileDeletePopupComponent,
        resolve: {
            profile: ProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
