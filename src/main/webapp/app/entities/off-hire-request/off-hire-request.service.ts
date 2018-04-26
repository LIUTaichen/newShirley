import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { OffHireRequest } from './off-hire-request.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OffHireRequest>;

@Injectable()
export class OffHireRequestService {

    private resourceUrl =  SERVER_API_URL + 'api/off-hire-requests';

    constructor(private http: HttpClient) { }

    create(offHireRequest: OffHireRequest): Observable<EntityResponseType> {
        const copy = this.convert(offHireRequest);
        return this.http.post<OffHireRequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(offHireRequest: OffHireRequest): Observable<EntityResponseType> {
        const copy = this.convert(offHireRequest);
        return this.http.put<OffHireRequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OffHireRequest>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OffHireRequest[]>> {
        const options = createRequestOption(req);
        return this.http.get<OffHireRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OffHireRequest[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OffHireRequest = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OffHireRequest[]>): HttpResponse<OffHireRequest[]> {
        const jsonResponse: OffHireRequest[] = res.body;
        const body: OffHireRequest[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OffHireRequest.
     */
    private convertItemFromServer(offHireRequest: OffHireRequest): OffHireRequest {
        const copy: OffHireRequest = Object.assign({}, offHireRequest);
        return copy;
    }

    /**
     * Convert a OffHireRequest to a JSON which can be sent to the server.
     */
    private convert(offHireRequest: OffHireRequest): OffHireRequest {
        const copy: OffHireRequest = Object.assign({}, offHireRequest);
        return copy;
    }
}
