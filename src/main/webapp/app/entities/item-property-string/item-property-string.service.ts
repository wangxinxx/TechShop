import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemPropertyString } from 'app/shared/model/item-property-string.model';

type EntityResponseType = HttpResponse<IItemPropertyString>;
type EntityArrayResponseType = HttpResponse<IItemPropertyString[]>;

@Injectable({ providedIn: 'root' })
export class ItemPropertyStringService {
    public resourceUrl = SERVER_API_URL + 'api/item-property-strings';

    constructor(protected http: HttpClient) {}

    create(itemPropertyString: IItemPropertyString): Observable<EntityResponseType> {
        return this.http.post<IItemPropertyString>(this.resourceUrl, itemPropertyString, { observe: 'response' });
    }

    update(itemPropertyString: IItemPropertyString): Observable<EntityResponseType> {
        return this.http.put<IItemPropertyString>(this.resourceUrl, itemPropertyString, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemPropertyString>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemPropertyString[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
