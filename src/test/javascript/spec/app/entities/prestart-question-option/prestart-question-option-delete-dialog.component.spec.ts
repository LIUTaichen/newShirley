/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartQuestionOptionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option-delete-dialog.component';
import { PrestartQuestionOptionService } from '../../../../../../main/webapp/app/entities/prestart-question-option/prestart-question-option.service';

describe('Component Tests', () => {

    describe('PrestartQuestionOption Management Delete Component', () => {
        let comp: PrestartQuestionOptionDeleteDialogComponent;
        let fixture: ComponentFixture<PrestartQuestionOptionDeleteDialogComponent>;
        let service: PrestartQuestionOptionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartQuestionOptionDeleteDialogComponent],
                providers: [
                    PrestartQuestionOptionService
                ]
            })
            .overrideTemplate(PrestartQuestionOptionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartQuestionOptionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartQuestionOptionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
