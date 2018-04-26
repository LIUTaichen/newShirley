/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { OffHireRequestComponent } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request.component';
import { OffHireRequestService } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request.service';
import { OffHireRequest } from '../../../../../../main/webapp/app/entities/off-hire-request/off-hire-request.model';

describe('Component Tests', () => {

    describe('OffHireRequest Management Component', () => {
        let comp: OffHireRequestComponent;
        let fixture: ComponentFixture<OffHireRequestComponent>;
        let service: OffHireRequestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [OffHireRequestComponent],
                providers: [
                    OffHireRequestService
                ]
            })
            .overrideTemplate(OffHireRequestComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffHireRequestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffHireRequestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OffHireRequest(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.offHireRequests[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
