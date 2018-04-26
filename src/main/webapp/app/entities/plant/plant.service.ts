import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Plant } from './plant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Plant>;

@Injectable()
export class PlantService {

    private resourceUrl =  SERVER_API_URL + 'api/plants';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(plant: Plant): Observable<EntityResponseType> {
        const copy = this.convert(plant);
        return this.http.post<Plant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(plant: Plant): Observable<EntityResponseType> {
        const copy = this.convert(plant);
        return this.http.put<Plant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Plant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Plant[]>> {
        const options = createRequestOption(req);
        return this.http.get<Plant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Plant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Plant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Plant[]>): HttpResponse<Plant[]> {
        const jsonResponse: Plant[] = res.body;
        const body: Plant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Plant.
     */
    private convertItemFromServer(plant: Plant): Plant {
        const copy: Plant = Object.assign({}, plant);
        copy.purchaseDate = this.dateUtils
            .convertDateTimeFromServer(plant.purchaseDate);
        copy.dateOfManufacture = this.dateUtils
            .convertDateTimeFromServer(plant.dateOfManufacture);
        copy.certificateDueDate = this.dateUtils
            .convertDateTimeFromServer(plant.certificateDueDate);
        copy.registrationDueDate = this.dateUtils
            .convertDateTimeFromServer(plant.registrationDueDate);
        copy.lastLocationUpdateTime = this.dateUtils
            .convertDateTimeFromServer(plant.lastLocationUpdateTime);
        return copy;
    }

    /**
     * Convert a Plant to a JSON which can be sent to the server.
     */
    private convert(plant: Plant): Plant {
        const copy: Plant = Object.assign({}, plant);

        copy.purchaseDate = this.dateUtils.toDate(plant.purchaseDate);

        copy.dateOfManufacture = this.dateUtils.toDate(plant.dateOfManufacture);

        copy.certificateDueDate = this.dateUtils.toDate(plant.certificateDueDate);

        copy.registrationDueDate = this.dateUtils.toDate(plant.registrationDueDate);

        copy.lastLocationUpdateTime = this.dateUtils.toDate(plant.lastLocationUpdateTime);
        return copy;
    }
}
