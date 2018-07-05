import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartQuestionOption } from './prestart-question-option.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartQuestionOption>;

@Injectable()
export class PrestartQuestionOptionService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-question-options';

    constructor(private http: HttpClient) { }

    create(prestartQuestionOption: PrestartQuestionOption): Observable<EntityResponseType> {
        const copy = this.convert(prestartQuestionOption);
        return this.http.post<PrestartQuestionOption>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartQuestionOption: PrestartQuestionOption): Observable<EntityResponseType> {
        const copy = this.convert(prestartQuestionOption);
        return this.http.put<PrestartQuestionOption>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartQuestionOption>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartQuestionOption[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartQuestionOption[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartQuestionOption[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartQuestionOption = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartQuestionOption[]>): HttpResponse<PrestartQuestionOption[]> {
        const jsonResponse: PrestartQuestionOption[] = res.body;
        const body: PrestartQuestionOption[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartQuestionOption.
     */
    private convertItemFromServer(prestartQuestionOption: PrestartQuestionOption): PrestartQuestionOption {
        const copy: PrestartQuestionOption = Object.assign({}, prestartQuestionOption);
        return copy;
    }

    /**
     * Convert a PrestartQuestionOption to a JSON which can be sent to the server.
     */
    private convert(prestartQuestionOption: PrestartQuestionOption): PrestartQuestionOption {
        const copy: PrestartQuestionOption = Object.assign({}, prestartQuestionOption);
        return copy;
    }
}
