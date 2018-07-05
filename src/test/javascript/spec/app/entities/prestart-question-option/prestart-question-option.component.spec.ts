/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartQuestionOptionComponent } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.component';
import { PrestartQuestionOptionService } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.service';
import { PrestartQuestionOption } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.model';

describe('Component Tests', () => {

    describe('PrestartQuestionOption Management Component', () => {
        let comp: PrestartQuestionOptionComponent;
        let fixture: ComponentFixture<PrestartQuestionOptionComponent>;
        let service: PrestartQuestionOptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartQuestionOptionComponent],
                providers: [
                    PrestartQuestionOptionService
                ]
            })
            .overrideTemplate(PrestartQuestionOptionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartQuestionOptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartQuestionOptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartQuestionOption(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartQuestionOptions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
