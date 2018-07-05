import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartQuestion } from './prestart-question.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartQuestion>;

@Injectable()
export class PrestartQuestionService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-questions';

    constructor(private http: HttpClient) { }

    create(prestartQuestion: PrestartQuestion): Observable<EntityResponseType> {
        const copy = this.convert(prestartQuestion);
        return this.http.post<PrestartQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartQuestion: PrestartQuestion): Observable<EntityResponseType> {
        const copy = this.convert(prestartQuestion);
        return this.http.put<PrestartQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartQuestion[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartQuestion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartQuestion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartQuestion[]>): HttpResponse<PrestartQuestion[]> {
        const jsonResponse: PrestartQuestion[] = res.body;
        const body: PrestartQuestion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartQuestion.
     */
    private convertItemFromServer(prestartQuestion: PrestartQuestion): PrestartQuestion {
        const copy: PrestartQuestion = Object.assign({}, prestartQuestion);
        return copy;
    }

    /**
     * Convert a PrestartQuestion to a JSON which can be sent to the server.
     */
    private convert(prestartQuestion: PrestartQuestion): PrestartQuestion {
        const copy: PrestartQuestion = Object.assign({}, prestartQuestion);
        return copy;
    }
}
