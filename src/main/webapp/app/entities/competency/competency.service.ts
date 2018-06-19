import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Competency } from './competency.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Competency>;

@Injectable()
export class CompetencyService {

    private resourceUrl =  SERVER_API_URL + 'api/competencies';

    constructor(private http: HttpClient) { }

    create(competency: Competency): Observable<EntityResponseType> {
        const copy = this.convert(competency);
        return this.http.post<Competency>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(competency: Competency): Observable<EntityResponseType> {
        const copy = this.convert(competency);
        return this.http.put<Competency>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Competency>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Competency[]>> {
        const options = createRequestOption(req);
        return this.http.get<Competency[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Competency[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Competency = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Competency[]>): HttpResponse<Competency[]> {
        const jsonResponse: Competency[] = res.body;
        const body: Competency[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Competency.
     */
    private convertItemFromServer(competency: Competency): Competency {
        const copy: Competency = Object.assign({}, competency);
        return copy;
    }

    /**
     * Convert a Competency to a JSON which can be sent to the server.
     */
    private convert(competency: Competency): Competency {
        const copy: Competency = Object.assign({}, competency);
        return copy;
    }
}
