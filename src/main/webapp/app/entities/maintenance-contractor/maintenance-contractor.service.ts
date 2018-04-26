import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MaintenanceContractor } from './maintenance-contractor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MaintenanceContractor>;

@Injectable()
export class MaintenanceContractorService {

    private resourceUrl =  SERVER_API_URL + 'api/maintenance-contractors';

    constructor(private http: HttpClient) { }

    create(maintenanceContractor: MaintenanceContractor): Observable<EntityResponseType> {
        const copy = this.convert(maintenanceContractor);
        return this.http.post<MaintenanceContractor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(maintenanceContractor: MaintenanceContractor): Observable<EntityResponseType> {
        const copy = this.convert(maintenanceContractor);
        return this.http.put<MaintenanceContractor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MaintenanceContractor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MaintenanceContractor[]>> {
        const options = createRequestOption(req);
        return this.http.get<MaintenanceContractor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MaintenanceContractor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MaintenanceContractor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MaintenanceContractor[]>): HttpResponse<MaintenanceContractor[]> {
        const jsonResponse: MaintenanceContractor[] = res.body;
        const body: MaintenanceContractor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MaintenanceContractor.
     */
    private convertItemFromServer(maintenanceContractor: MaintenanceContractor): MaintenanceContractor {
        const copy: MaintenanceContractor = Object.assign({}, maintenanceContractor);
        return copy;
    }

    /**
     * Convert a MaintenanceContractor to a JSON which can be sent to the server.
     */
    private convert(maintenanceContractor: MaintenanceContractor): MaintenanceContractor {
        const copy: MaintenanceContractor = Object.assign({}, maintenanceContractor);
        return copy;
    }
}
