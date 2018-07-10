/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckResponseDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response-dialog.component';
import { PrestartCheckResponseService } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.service';
import { PrestartCheckResponse } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.model';
import { PrestartCheckService } from '../../../../../../main/webapp/app/entities/prestart-check';
import { PrestartQuestionService } from '../../../../../../main/webapp/app/entities/prestart-question';
import { PrestartQuestionOptionService } from '../../../../../../main/webapp/app/entities/prestart-question-option';

describe('Component Tests', () => {

    describe('PrestartCheckResponse Management Dialog Component', () => {
        let comp: PrestartCheckResponseDialogComponent;
        let fixture: ComponentFixture<PrestartCheckResponseDialogComponent>;
        let service: PrestartCheckResponseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckResponseDialogComponent],
                providers: [
                    PrestartCheckService,
                    PrestartQuestionService,
                    PrestartQuestionOptionService,
                    PrestartCheckResponseService
                ]
            })
            .overrideTemplate(PrestartCheckResponseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckResponseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckResponseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckResponse(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckResponse();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
