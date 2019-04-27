import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';

type EntityResponseType = HttpResponse<IItemPropertyFloat>;
type EntityArrayResponseType = HttpResponse<IItemPropertyFloat[]>;

@Injectable({ providedIn: 'root' })
export class ItemPropertyFloatService {
    public resourceUrl = SERVER_API_URL + 'api/item-property-floats';

    constructor(protected http: HttpClient) {}

    create(itemPropertyFloat: IItemPropertyFloat): Observable<EntityResponseType> {
        return this.http.post<IItemPropertyFloat>(this.resourceUrl, itemPropertyFloat, { observe: 'response' });
    }

    update(itemPropertyFloat: IItemPropertyFloat): Observable<EntityResponseType> {
        return this.http.put<IItemPropertyFloat>(this.resourceUrl, itemPropertyFloat, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemPropertyFloat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemPropertyFloat[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
