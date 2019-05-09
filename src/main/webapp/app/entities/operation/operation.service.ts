import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperation } from 'app/shared/model/operation.model';

type EntityResponseType = HttpResponse<IOperation>;
type EntityArrayResponseType = HttpResponse<IOperation[]>;

@Injectable({ providedIn: 'root' })
export class OperationService {
    public resourceUrl = SERVER_API_URL + 'api/operations';

    constructor(protected http: HttpClient) {}

    create(operation: IOperation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(operation);
        return this.http
            .post<IOperation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(operation: IOperation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(operation);
        return this.http
            .put<IOperation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOperation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOperation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(operation: IOperation): IOperation {
        const copy: IOperation = Object.assign({}, operation, {
            orderDate: operation.orderDate != null && operation.orderDate.isValid() ? operation.orderDate.format(DATE_FORMAT) : null,
            approveDate:
                operation.approveDate != null && operation.approveDate.isValid() ? operation.approveDate.format(DATE_FORMAT) : null,
            deliveryDate:
                operation.deliveryDate != null && operation.deliveryDate.isValid() ? operation.deliveryDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
            res.body.approveDate = res.body.approveDate != null ? moment(res.body.approveDate) : null;
            res.body.deliveryDate = res.body.deliveryDate != null ? moment(res.body.deliveryDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((operation: IOperation) => {
                operation.orderDate = operation.orderDate != null ? moment(operation.orderDate) : null;
                operation.approveDate = operation.approveDate != null ? moment(operation.approveDate) : null;
                operation.deliveryDate = operation.deliveryDate != null ? moment(operation.deliveryDate) : null;
            });
        }
        return res;
    }
}
