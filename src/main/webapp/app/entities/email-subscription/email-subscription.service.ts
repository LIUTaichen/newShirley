import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EmailSubscription } from './email-subscription.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmailSubscription>;

@Injectable()
export class EmailSubscriptionService {

    private resourceUrl =  SERVER_API_URL + 'api/email-subscriptions';

    constructor(private http: HttpClient) { }

    create(emailSubscription: EmailSubscription): Observable<EntityResponseType> {
        const copy = this.convert(emailSubscription);
        return this.http.post<EmailSubscription>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(emailSubscription: EmailSubscription): Observable<EntityResponseType> {
        const copy = this.convert(emailSubscription);
        return this.http.put<EmailSubscription>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmailSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmailSubscription[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmailSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmailSubscription[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmailSubscription = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmailSubscription[]>): HttpResponse<EmailSubscription[]> {
        const jsonResponse: EmailSubscription[] = res.body;
        const body: EmailSubscription[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmailSubscription.
     */
    private convertItemFromServer(emailSubscription: EmailSubscription): EmailSubscription {
        const copy: EmailSubscription = Object.assign({}, emailSubscription);
        return copy;
    }

    /**
     * Convert a EmailSubscription to a JSON which can be sent to the server.
     */
    private convert(emailSubscription: EmailSubscription): EmailSubscription {
        const copy: EmailSubscription = Object.assign({}, emailSubscription);
        return copy;
    }
}
