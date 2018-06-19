import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PlantLog } from './plant-log.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PlantLog>;

@Injectable()
export class PlantLogService {

    private resourceUrl =  SERVER_API_URL + 'api/plant-logs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(plantLog: PlantLog): Observable<EntityResponseType> {
        const copy = this.convert(plantLog);
        return this.http.post<PlantLog>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(plantLog: PlantLog): Observable<EntityResponseType> {
        const copy = this.convert(plantLog);
        return this.http.put<PlantLog>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PlantLog>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PlantLog[]>> {
        const options = createRequestOption(req);
        return this.http.get<PlantLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PlantLog[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PlantLog = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PlantLog[]>): HttpResponse<PlantLog[]> {
        const jsonResponse: PlantLog[] = res.body;
        const body: PlantLog[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PlantLog.
     */
    private convertItemFromServer(plantLog: PlantLog): PlantLog {
        const copy: PlantLog = Object.assign({}, plantLog);
        copy.wofDueDate = this.dateUtils
            .convertDateTimeFromServer(plantLog.wofDueDate);
        copy.cofDueDate = this.dateUtils
            .convertDateTimeFromServer(plantLog.cofDueDate);
        copy.serviceDueDate = this.dateUtils
            .convertDateTimeFromServer(plantLog.serviceDueDate);
        return copy;
    }

    /**
     * Convert a PlantLog to a JSON which can be sent to the server.
     */
    private convert(plantLog: PlantLog): PlantLog {
        const copy: PlantLog = Object.assign({}, plantLog);

        copy.wofDueDate = this.dateUtils.toDate(plantLog.wofDueDate);

        copy.cofDueDate = this.dateUtils.toDate(plantLog.cofDueDate);

        copy.serviceDueDate = this.dateUtils.toDate(plantLog.serviceDueDate);
        return copy;
    }
}
