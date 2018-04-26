/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { OffHireRequestDetailComponent } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request-detail.component';
import { OffHireRequestService } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request.service';
import { OffHireRequest } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request.model';

describe('Component Tests', () => {

    describe('OffHireRequest Management Detail Component', () => {
        let comp: OffHireRequestDetailComponent;
        let fixture: ComponentFixture<OffHireRequestDetailComponent>;
        let service: OffHireRequestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [OffHireRequestDetailComponent],
                providers: [
                    OffHireRequestService
                ]
            })
            .overrideTemplate(OffHireRequestDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffHireRequestDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffHireRequestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OffHireRequest(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.offHireRequest).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
