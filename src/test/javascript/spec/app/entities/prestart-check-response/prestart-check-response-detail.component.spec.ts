/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckResponseDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response-detail.component';
import { PrestartCheckResponseService } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.service';
import { PrestartCheckResponse } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.model';

describe('Component Tests', () => {

    describe('PrestartCheckResponse Management Detail Component', () => {
        let comp: PrestartCheckResponseDetailComponent;
        let fixture: ComponentFixture<PrestartCheckResponseDetailComponent>;
        let service: PrestartCheckResponseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckResponseDetailComponent],
                providers: [
                    PrestartCheckResponseService
                ]
            })
            .overrideTemplate(PrestartCheckResponseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckResponseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckResponseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartCheckResponse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartCheckResponse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
