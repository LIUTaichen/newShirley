import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartCheckResponse } from './prestart-check-response.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartCheckResponse>;

@Injectable()
export class PrestartCheckResponseService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-check-responses';

    constructor(private http: HttpClient) { }

    create(prestartCheckResponse: PrestartCheckResponse): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckResponse);
        return this.http.post<PrestartCheckResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartCheckResponse: PrestartCheckResponse): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckResponse);
        return this.http.put<PrestartCheckResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartCheckResponse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartCheckResponse[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartCheckResponse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartCheckResponse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartCheckResponse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartCheckResponse[]>): HttpResponse<PrestartCheckResponse[]> {
        const jsonResponse: PrestartCheckResponse[] = res.body;
        const body: PrestartCheckResponse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartCheckResponse.
     */
    private convertItemFromServer(prestartCheckResponse: PrestartCheckResponse): PrestartCheckResponse {
        const copy: PrestartCheckResponse = Object.assign({}, prestartCheckResponse);
        return copy;
    }

    /**
     * Convert a PrestartCheckResponse to a JSON which can be sent to the server.
     */
    private convert(prestartCheckResponse: PrestartCheckResponse): PrestartCheckResponse {
        const copy: PrestartCheckResponse = Object.assign({}, prestartCheckResponse);
        return copy;
    }
}
