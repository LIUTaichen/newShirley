import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartCheck } from './prestart-check.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartCheck>;

@Injectable()
export class PrestartCheckService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-checks';

    constructor(private http: HttpClient) { }

    create(prestartCheck: PrestartCheck): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheck);
        return this.http.post<PrestartCheck>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartCheck: PrestartCheck): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheck);
        return this.http.put<PrestartCheck>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartCheck>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartCheck[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartCheck[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartCheck[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartCheck = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartCheck[]>): HttpResponse<PrestartCheck[]> {
        const jsonResponse: PrestartCheck[] = res.body;
        const body: PrestartCheck[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartCheck.
     */
    private convertItemFromServer(prestartCheck: PrestartCheck): PrestartCheck {
        const copy: PrestartCheck = Object.assign({}, prestartCheck);
        return copy;
    }

    /**
     * Convert a PrestartCheck to a JSON which can be sent to the server.
     */
    private convert(prestartCheck: PrestartCheck): PrestartCheck {
        const copy: PrestartCheck = Object.assign({}, prestartCheck);
        return copy;
    }
}
