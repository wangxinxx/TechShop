import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfile } from 'app/shared/model/profile.model';

type EntityResponseType = HttpResponse<IProfile>;
type EntityArrayResponseType = HttpResponse<IProfile[]>;

@Injectable({ providedIn: 'root' })
export class ProfileService {
    public resourceUrl = SERVER_API_URL + 'api/profiles';

    constructor(protected http: HttpClient) {}

    create(profile: IProfile): Observable<EntityResponseType> {
        return this.http.post<IProfile>(this.resourceUrl, profile, { observe: 'response' });
    }

    update(profile: IProfile): Observable<EntityResponseType> {
        return this.http.put<IProfile>(this.resourceUrl, profile, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
