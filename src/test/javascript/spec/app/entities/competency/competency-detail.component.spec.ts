/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { CompetencyDetailComponent } from '../../../../../../main/webapp/app/entities/competency/competency-detail.component';
import { CompetencyService } from '../../../../../../main/webapp/app/entities/competency/competency.service';
import { Competency } from '../../../../../../main/webapp/app/entities/competency/competency.model';

describe('Component Tests', () => {

    describe('Competency Management Detail Component', () => {
        let comp: CompetencyDetailComponent;
        let fixture: ComponentFixture<CompetencyDetailComponent>;
        let service: CompetencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [CompetencyDetailComponent],
                providers: [
                    CompetencyService
                ]
            })
            .overrideTemplate(CompetencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Competency(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.competency).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
