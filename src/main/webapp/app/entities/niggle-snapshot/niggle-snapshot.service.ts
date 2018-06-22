import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { NiggleSnapshot } from './niggle-snapshot.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NiggleSnapshot>;

@Injectable()
export class NiggleSnapshotService {

    private resourceUrl =  SERVER_API_URL + 'api/niggle-snapshots';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(niggleSnapshot: NiggleSnapshot): Observable<EntityResponseType> {
        const copy = this.convert(niggleSnapshot);
        return this.http.post<NiggleSnapshot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(niggleSnapshot: NiggleSnapshot): Observable<EntityResponseType> {
        const copy = this.convert(niggleSnapshot);
        return this.http.put<NiggleSnapshot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NiggleSnapshot>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NiggleSnapshot[]>> {
        const options = createRequestOption(req);
        return this.http.get<NiggleSnapshot[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NiggleSnapshot[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NiggleSnapshot = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NiggleSnapshot[]>): HttpResponse<NiggleSnapshot[]> {
        const jsonResponse: NiggleSnapshot[] = res.body;
        const body: NiggleSnapshot[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NiggleSnapshot.
     */
    private convertItemFromServer(niggleSnapshot: NiggleSnapshot): NiggleSnapshot {
        const copy: NiggleSnapshot = Object.assign({}, niggleSnapshot);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(niggleSnapshot.date);
        return copy;
    }

    /**
     * Convert a NiggleSnapshot to a JSON which can be sent to the server.
     */
    private convert(niggleSnapshot: NiggleSnapshot): NiggleSnapshot {
        const copy: NiggleSnapshot = Object.assign({}, niggleSnapshot);
        copy.date = this.dateUtils
            .convertLocalDateToServer(niggleSnapshot.date);
        return copy;
    }
}
