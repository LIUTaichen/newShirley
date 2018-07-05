/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckConfigComponent } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.component';
import { PrestartCheckConfigService } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.service';
import { PrestartCheckConfig } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.model';

describe('Component Tests', () => {

    describe('PrestartCheckConfig Management Component', () => {
        let comp: PrestartCheckConfigComponent;
        let fixture: ComponentFixture<PrestartCheckConfigComponent>;
        let service: PrestartCheckConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckConfigComponent],
                providers: [
                    PrestartCheckConfigService
                ]
            })
            .overrideTemplate(PrestartCheckConfigComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckConfigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartCheckConfig(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartCheckConfigs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
