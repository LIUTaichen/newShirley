import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Niggle } from './niggle.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Niggle>;

@Injectable()
export class NiggleService {

    private resourceUrl =  SERVER_API_URL + 'api/niggles';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(niggle: Niggle): Observable<EntityResponseType> {
        const copy = this.convert(niggle);
        return this.http.post<Niggle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(niggle: Niggle): Observable<EntityResponseType> {
        const copy = this.convert(niggle);
        return this.http.put<Niggle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Niggle>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Niggle[]>> {
        const options = createRequestOption(req);
        return this.http.get<Niggle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Niggle[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Niggle = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Niggle[]>): HttpResponse<Niggle[]> {
        const jsonResponse: Niggle[] = res.body;
        const body: Niggle[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Niggle.
     */
    private convertItemFromServer(niggle: Niggle): Niggle {
        const copy: Niggle = Object.assign({}, niggle);
        copy.dateOpened = this.dateUtils
            .convertDateTimeFromServer(niggle.dateOpened);
        copy.dateClosed = this.dateUtils
            .convertDateTimeFromServer(niggle.dateClosed);
        return copy;
    }

    /**
     * Convert a Niggle to a JSON which can be sent to the server.
     */
    private convert(niggle: Niggle): Niggle {
        const copy: Niggle = Object.assign({}, niggle);

        return copy;
    }
}
