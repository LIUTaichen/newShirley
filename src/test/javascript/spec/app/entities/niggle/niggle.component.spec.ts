/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleComponent } from '../../../../../../main/webapp/app/entities/niggle/niggle.component';
import { NiggleService } from '../../../../../../main/webapp/app/entities/niggle/niggle.service';
import { Niggle } from '../../../../../../main/webapp/app/entities/niggle/niggle.model';

describe('Component Tests', () => {

    describe('Niggle Management Component', () => {
        let comp: NiggleComponent;
        let fixture: ComponentFixture<NiggleComponent>;
        let service: NiggleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleComponent],
                providers: [
                    NiggleService
                ]
            })
            .overrideTemplate(NiggleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Niggle(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.niggles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
