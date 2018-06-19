import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WeeklyNiggleSnapshot } from './weekly-niggle-snapshot.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WeeklyNiggleSnapshot>;

@Injectable()
export class WeeklyNiggleSnapshotService {

    private resourceUrl =  SERVER_API_URL + 'api/weekly-niggle-snapshots';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(weeklyNiggleSnapshot: WeeklyNiggleSnapshot): Observable<EntityResponseType> {
        const copy = this.convert(weeklyNiggleSnapshot);
        return this.http.post<WeeklyNiggleSnapshot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(weeklyNiggleSnapshot: WeeklyNiggleSnapshot): Observable<EntityResponseType> {
        const copy = this.convert(weeklyNiggleSnapshot);
        return this.http.put<WeeklyNiggleSnapshot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WeeklyNiggleSnapshot>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WeeklyNiggleSnapshot[]>> {
        const options = createRequestOption(req);
        return this.http.get<WeeklyNiggleSnapshot[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WeeklyNiggleSnapshot[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WeeklyNiggleSnapshot = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WeeklyNiggleSnapshot[]>): HttpResponse<WeeklyNiggleSnapshot[]> {
        const jsonResponse: WeeklyNiggleSnapshot[] = res.body;
        const body: WeeklyNiggleSnapshot[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WeeklyNiggleSnapshot.
     */
    private convertItemFromServer(weeklyNiggleSnapshot: WeeklyNiggleSnapshot): WeeklyNiggleSnapshot {
        const copy: WeeklyNiggleSnapshot = Object.assign({}, weeklyNiggleSnapshot);
        copy.weekEndingOn = this.dateUtils
            .convertLocalDateFromServer(weeklyNiggleSnapshot.weekEndingOn);
        return copy;
    }

    /**
     * Convert a WeeklyNiggleSnapshot to a JSON which can be sent to the server.
     */
    private convert(weeklyNiggleSnapshot: WeeklyNiggleSnapshot): WeeklyNiggleSnapshot {
        const copy: WeeklyNiggleSnapshot = Object.assign({}, weeklyNiggleSnapshot);
        copy.weekEndingOn = this.dateUtils
            .convertLocalDateToServer(weeklyNiggleSnapshot.weekEndingOn);
        return copy;
    }
}
