/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckConfigDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config-detail.component';
import { PrestartCheckConfigService } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.service';
import { PrestartCheckConfig } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.model';

describe('Component Tests', () => {

    describe('PrestartCheckConfig Management Detail Component', () => {
        let comp: PrestartCheckConfigDetailComponent;
        let fixture: ComponentFixture<PrestartCheckConfigDetailComponent>;
        let service: PrestartCheckConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckConfigDetailComponent],
                providers: [
                    PrestartCheckConfigService
                ]
            })
            .overrideTemplate(PrestartCheckConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartCheckConfig(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartCheckConfig).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
