import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';

type EntityResponseType = HttpResponse<IItemPropertyDouble>;
type EntityArrayResponseType = HttpResponse<IItemPropertyDouble[]>;

@Injectable({ providedIn: 'root' })
export class ItemPropertyDoubleService {
    public resourceUrl = SERVER_API_URL + 'api/item-property-doubles';

    constructor(protected http: HttpClient) {}

    create(itemPropertyDouble: IItemPropertyDouble): Observable<EntityResponseType> {
        return this.http.post<IItemPropertyDouble>(this.resourceUrl, itemPropertyDouble, { observe: 'response' });
    }

    update(itemPropertyDouble: IItemPropertyDouble): Observable<EntityResponseType> {
        return this.http.put<IItemPropertyDouble>(this.resourceUrl, itemPropertyDouble, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemPropertyDouble>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemPropertyDouble[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
