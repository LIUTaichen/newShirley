import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { People } from './people.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<People>;

@Injectable()
export class PeopleService {

    private resourceUrl =  SERVER_API_URL + 'api/people';

    constructor(private http: HttpClient) { }

    create(people: People): Observable<EntityResponseType> {
        const copy = this.convert(people);
        return this.http.post<People>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(people: People): Observable<EntityResponseType> {
        const copy = this.convert(people);
        return this.http.put<People>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<People>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<People[]>> {
        const options = createRequestOption(req);
        return this.http.get<People[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<People[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: People = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<People[]>): HttpResponse<People[]> {
        const jsonResponse: People[] = res.body;
        const body: People[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to People.
     */
    private convertItemFromServer(people: People): People {
        const copy: People = Object.assign({}, people);
        return copy;
    }

    /**
     * Convert a People to a JSON which can be sent to the server.
     */
    private convert(people: People): People {
        const copy: People = Object.assign({}, people);
        return copy;
    }
}
