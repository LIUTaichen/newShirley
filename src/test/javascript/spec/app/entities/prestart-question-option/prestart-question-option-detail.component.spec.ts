/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartQuestionOptionDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option-detail.component';
import { PrestartQuestionOptionService } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.service';
import { PrestartQuestionOption } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.model';

describe('Component Tests', () => {

    describe('PrestartQuestionOption Management Detail Component', () => {
        let comp: PrestartQuestionOptionDetailComponent;
        let fixture: ComponentFixture<PrestartQuestionOptionDetailComponent>;
        let service: PrestartQuestionOptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartQuestionOptionDetailComponent],
                providers: [
                    PrestartQuestionOptionService
                ]
            })
            .overrideTemplate(PrestartQuestionOptionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartQuestionOptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartQuestionOptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartQuestionOption(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartQuestionOption).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
