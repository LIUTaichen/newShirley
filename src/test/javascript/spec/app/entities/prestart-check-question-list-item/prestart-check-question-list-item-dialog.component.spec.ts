/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckQuestionListItemDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item-dialog.component';
import { PrestartCheckQuestionListItemService } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.service';
import { PrestartCheckQuestionListItem } from '../../../../../../main/webapp/app/entities/prestart-check-question-list-item/prestart-check-question-list-item.model';
import { PrestartCheckConfigService } from '../../../../../../main/webapp/app/entities/prestart-check-config';
import { PrestartQuestionService } from '../../../../../../main/webapp/app/entities/prestart-question';

describe('Component Tests', () => {

    describe('PrestartCheckQuestionListItem Management Dialog Component', () => {
        let comp: PrestartCheckQuestionListItemDialogComponent;
        let fixture: ComponentFixture<PrestartCheckQuestionListItemDialogComponent>;
        let service: PrestartCheckQuestionListItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckQuestionListItemDialogComponent],
                providers: [
                    PrestartCheckConfigService,
                    PrestartQuestionService,
                    PrestartCheckQuestionListItemService
                ]
            })
            .overrideTemplate(PrestartCheckQuestionListItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckQuestionListItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckQuestionListItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckQuestionListItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckQuestionListItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckQuestionListItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckQuestionListItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckQuestionListItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckQuestionListItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
