/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { CompetencyComponent } from '../../../../../../main/webapp/app/entities/competency/competency.component';
import { CompetencyService } from '../../../../../../main/webapp/app/entities/competency/competency.service';
import { Competency } from '../../../../../../main/webapp/app/entities/competency/competency.model';

describe('Component Tests', () => {

    describe('Competency Management Component', () => {
        let comp: CompetencyComponent;
        let fixture: ComponentFixture<CompetencyComponent>;
        let service: CompetencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [CompetencyComponent],
                providers: [
                    CompetencyService
                ]
            })
            .overrideTemplate(CompetencyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetencyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Competency(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.competencies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
