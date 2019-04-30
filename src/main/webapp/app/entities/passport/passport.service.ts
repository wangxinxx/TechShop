import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPassport } from 'app/shared/model/passport.model';

type EntityResponseType = HttpResponse<IPassport>;
type EntityArrayResponseType = HttpResponse<IPassport[]>;

@Injectable({ providedIn: 'root' })
export class PassportService {
    public resourceUrl = SERVER_API_URL + 'api/passports';

    constructor(protected http: HttpClient) {}

    create(passport: IPassport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(passport);
        return this.http
            .post<IPassport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(passport: IPassport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(passport);
        return this.http
            .put<IPassport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPassport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPassport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(passport: IPassport): IPassport {
        const copy: IPassport = Object.assign({}, passport, {
            dob: passport.dob != null && passport.dob.isValid() ? passport.dob.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dob = res.body.dob != null ? moment(res.body.dob) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((passport: IPassport) => {
                passport.dob = passport.dob != null ? moment(passport.dob) : null;
            });
        }
        return res;
    }
}
