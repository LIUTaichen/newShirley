/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckComponent } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.component';
import { PrestartCheckService } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.service';
import { PrestartCheck } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.model';

describe('Component Tests', () => {

    describe('PrestartCheck Management Component', () => {
        let comp: PrestartCheckComponent;
        let fixture: ComponentFixture<PrestartCheckComponent>;
        let service: PrestartCheckService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckComponent],
                providers: [
                    PrestartCheckService
                ]
            })
            .overrideTemplate(PrestartCheckComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartCheck(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartChecks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
