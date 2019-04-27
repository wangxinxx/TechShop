import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemPropertyInt } from 'app/shared/model/item-property-int.model';

type EntityResponseType = HttpResponse<IItemPropertyInt>;
type EntityArrayResponseType = HttpResponse<IItemPropertyInt[]>;

@Injectable({ providedIn: 'root' })
export class ItemPropertyIntService {
    public resourceUrl = SERVER_API_URL + 'api/item-property-ints';

    constructor(protected http: HttpClient) {}

    create(itemPropertyInt: IItemPropertyInt): Observable<EntityResponseType> {
        return this.http.post<IItemPropertyInt>(this.resourceUrl, itemPropertyInt, { observe: 'response' });
    }

    update(itemPropertyInt: IItemPropertyInt): Observable<EntityResponseType> {
        return this.http.put<IItemPropertyInt>(this.resourceUrl, itemPropertyInt, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemPropertyInt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemPropertyInt[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
