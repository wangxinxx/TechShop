import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemPropertyBool } from 'app/shared/model/item-property-bool.model';

type EntityResponseType = HttpResponse<IItemPropertyBool>;
type EntityArrayResponseType = HttpResponse<IItemPropertyBool[]>;

@Injectable({ providedIn: 'root' })
export class ItemPropertyBoolService {
    public resourceUrl = SERVER_API_URL + 'api/item-property-bools';

    constructor(protected http: HttpClient) {}

    create(itemPropertyBool: IItemPropertyBool): Observable<EntityResponseType> {
        return this.http.post<IItemPropertyBool>(this.resourceUrl, itemPropertyBool, { observe: 'response' });
    }

    update(itemPropertyBool: IItemPropertyBool): Observable<EntityResponseType> {
        return this.http.put<IItemPropertyBool>(this.resourceUrl, itemPropertyBool, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemPropertyBool>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemPropertyBool[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
