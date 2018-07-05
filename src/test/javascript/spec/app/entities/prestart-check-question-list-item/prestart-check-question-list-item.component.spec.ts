/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckQuestionListItemComponent } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.component';
import { PrestartCheckQuestionListItemService } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.service';
import { PrestartCheckQuestionListItem } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.model';

describe('Component Tests', () => {

    describe('PrestartCheckQuestionListItem Management Component', () => {
        let comp: PrestartCheckQuestionListItemComponent;
        let fixture: ComponentFixture<PrestartCheckQuestionListItemComponent>;
        let service: PrestartCheckQuestionListItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckQuestionListItemComponent],
                providers: [
                    PrestartCheckQuestionListItemService
                ]
            })
            .overrideTemplate(PrestartCheckQuestionListItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckQuestionListItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckQuestionListItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrestartCheckQuestionListItem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prestartCheckQuestionListItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
