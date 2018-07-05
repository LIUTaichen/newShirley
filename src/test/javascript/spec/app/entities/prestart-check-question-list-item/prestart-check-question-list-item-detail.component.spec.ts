/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckQuestionListItemDetailComponent } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item-detail.component';
import { PrestartCheckQuestionListItemService } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.service';
import { PrestartCheckQuestionListItem } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.model';

describe('Component Tests', () => {

    describe('PrestartCheckQuestionListItem Management Detail Component', () => {
        let comp: PrestartCheckQuestionListItemDetailComponent;
        let fixture: ComponentFixture<PrestartCheckQuestionListItemDetailComponent>;
        let service: PrestartCheckQuestionListItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckQuestionListItemDetailComponent],
                providers: [
                    PrestartCheckQuestionListItemService
                ]
            })
            .overrideTemplate(PrestartCheckQuestionListItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckQuestionListItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckQuestionListItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrestartCheckQuestionListItem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prestartCheckQuestionListItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
