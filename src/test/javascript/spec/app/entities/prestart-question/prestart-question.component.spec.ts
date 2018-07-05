/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartQuestionComponent } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question.component';
import { PrestartQuestionService } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question.service';
import { PrestartQuestion } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question.model';

describe('Component Tests', () => {

    describe('PrestartQuestion Management Component', () => {
        let comp: PrestartQuestionComponent;
        let fixture: ComponentFixture<PrestartQuestionComponent>;
        let service: PrestartQuestionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartQuestionComponent],
                providers: [
                    PrestartQuestionService
                ]
            })
            .overrideTemplate(PrestartQuestionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartQuestionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartQuestionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartQuestion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartQuestions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
