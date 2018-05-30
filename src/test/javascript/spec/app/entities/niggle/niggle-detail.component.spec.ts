/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleDetailComponent } from '../../../../../../main/webapp/app/entities/niggle/niggle-detail.component';
import { NiggleService } from '../../../../../../main/webapp/app/entities/niggle/niggle.service';
import { Niggle } from '../../../../../../main/webapp/app/entities/niggle/niggle.model';

describe('Component Tests', () => {

    describe('Niggle Management Detail Component', () => {
        let comp: NiggleDetailComponent;
        let fixture: ComponentFixture<NiggleDetailComponent>;
        let service: NiggleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleDetailComponent],
                providers: [
                    NiggleService
                ]
            })
            .overrideTemplate(NiggleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Niggle(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.niggle).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
