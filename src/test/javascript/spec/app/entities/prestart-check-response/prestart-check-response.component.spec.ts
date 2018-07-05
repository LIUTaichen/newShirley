/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckResponseComponent } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.component';
import { PrestartCheckResponseService } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.service';
import { PrestartCheckResponse } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.model';

describe('Component Tests', () => {

    describe('PrestartCheckResponse Management Component', () => {
        let comp: PrestartCheckResponseComponent;
        let fixture: ComponentFixture<PrestartCheckResponseComponent>;
        let service: PrestartCheckResponseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckResponseComponent],
                providers: [
                    PrestartCheckResponseService
                ]
            })
            .overrideTemplate(PrestartCheckResponseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckResponseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckResponseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartCheckResponse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartCheckResponses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
