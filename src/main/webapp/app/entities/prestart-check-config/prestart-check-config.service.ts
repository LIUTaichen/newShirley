import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrestartCheckConfig } from './prestart-check-config.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PrestartCheckConfig>;

@Injectable()
export class PrestartCheckConfigService {

    private resourceUrl =  SERVER_API_URL + 'api/prestart-check-configs';

    constructor(private http: HttpClient) { }

    create(prestartCheckConfig: PrestartCheckConfig): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckConfig);
        return this.http.post<PrestartCheckConfig>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestartCheckConfig: PrestartCheckConfig): Observable<EntityResponseType> {
        const copy = this.convert(prestartCheckConfig);
        return this.http.put<PrestartCheckConfig>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrestartCheckConfig>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrestartCheckConfig[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrestartCheckConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrestartCheckConfig[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrestartCheckConfig = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrestartCheckConfig[]>): HttpResponse<PrestartCheckConfig[]> {
        const jsonResponse: PrestartCheckConfig[] = res.body;
        const body: PrestartCheckConfig[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrestartCheckConfig.
     */
    private convertItemFromServer(prestartCheckConfig: PrestartCheckConfig): PrestartCheckConfig {
        const copy: PrestartCheckConfig = Object.assign({}, prestartCheckConfig);
        return copy;
    }

    /**
     * Convert a PrestartCheckConfig to a JSON which can be sent to the server.
     */
    private convert(prestartCheckConfig: PrestartCheckConfig): PrestartCheckConfig {
        const copy: PrestartCheckConfig = Object.assign({}, prestartCheckConfig);
        return copy;
    }
}
