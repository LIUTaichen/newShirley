import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartCheckQuestionListItem } from './prestart-check-question-list-item.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartCheckQuestionListItem>;

@Injectable()
export class PrestartCheckQuestionListItemService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-check-question-list-items';

    constructor(private http: HttpClient) { }

    create(prestartCheckQuestionListItem: PrestartCheckQuestionListItem): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckQuestionListItem);
        return this.http.post<PrestartCheckQuestionListItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartCheckQuestionListItem: PrestartCheckQuestionListItem): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckQuestionListItem);
        return this.http.put<PrestartCheckQuestionListItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartCheckQuestionListItem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartCheckQuestionListItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartCheckQuestionListItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartCheckQuestionListItem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartCheckQuestionListItem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartCheckQuestionListItem[]>): HttpResponse<PrestartCheckQuestionListItem[]> {
        const jsonResponse: PrestartCheckQuestionListItem[] = res.body;
        const body: PrestartCheckQuestionListItem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartCheckQuestionListItem.
     */
    private convertItemFromServer(prestartCheckQuestionListItem: PrestartCheckQuestionListItem): PrestartCheckQuestionListItem {
        const copy: PrestartCheckQuestionListItem = Object.assign({}, prestartCheckQuestionListItem);
        return copy;
    }

    /**
     * Convert a PrestartCheckQuestionListItem to a JSON which can be sent to the server.
     */
    private convert(prestartCheckQuestionListItem: PrestartCheckQuestionListItem): PrestartCheckQuestionListItem {
        const copy: PrestartCheckQuestionListItem = Object.assign({}, prestartCheckQuestionListItem);
        return copy;
    }
}
