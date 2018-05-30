import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PurchaseOrder } from './purchase-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PurchaseOrder>;

@Injectable()
export class PurchaseOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/purchase-orders';

    constructor(private http: HttpClient) { }

    create(purchaseOrder: PurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convert(purchaseOrder);
        return this.http.post<PurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(purchaseOrder: PurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convert(purchaseOrder);
        return this.http.put<PurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PurchaseOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<PurchaseOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PurchaseOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PurchaseOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PurchaseOrder[]>): HttpResponse<PurchaseOrder[]> {
        const jsonResponse: PurchaseOrder[] = res.body;
        const body: PurchaseOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PurchaseOrder.
     */
    private convertItemFromServer(purchaseOrder: PurchaseOrder): PurchaseOrder {
        const copy: PurchaseOrder = Object.assign({}, purchaseOrder);
        return copy;
    }

    /**
     * Convert a PurchaseOrder to a JSON which can be sent to the server.
     */
    private convert(purchaseOrder: PurchaseOrder): PurchaseOrder {
        const copy: PurchaseOrder = Object.assign({}, purchaseOrder);
        return copy;
    }
}
