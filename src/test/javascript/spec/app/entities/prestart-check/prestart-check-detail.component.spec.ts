/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check-detail.component';
import { PrestartCheckService } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.service';
import { PrestartCheck } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.model';

describe('Component Tests', () => {

    describe('PrestartCheck Management Detail Component', () => {
        let comp: PrestartCheckDetailComponent;
        let fixture: ComponentFixture<PrestartCheckDetailComponent>;
        let service: PrestartCheckService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckDetailComponent],
                providers: [
                    PrestartCheckService
                ]
            })
            .overrideTemplate(PrestartCheckDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartCheck(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartCheck).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
