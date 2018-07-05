/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartQuestionDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question-detail.component';
import { PrestartQuestionService } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question.service';
import { PrestartQuestion } from '../../../../../../main/webapp/app/entities/prestart-question/prestart-question.model';

describe('Component Tests', () => {

    describe('PrestartQuestion Management Detail Component', () => {
        let comp: PrestartQuestionDetailComponent;
        let fixture: ComponentFixture<PrestartQuestionDetailComponent>;
        let service: PrestartQuestionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartQuestionDetailComponent],
                providers: [
                    PrestartQuestionService
                ]
            })
            .overrideTemplate(PrestartQuestionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartQuestionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartQuestionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartQuestion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartQuestion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
