/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckResponseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response-delete-dialog.component';
import { PrestartCheckResponseService } from '../../../../../../main/webapp/app/entities/prestart-check-response/prestart-check-response.service';

describe('Component Tests', () => {

    describe('PrestartCheckResponse Management Delete Component', () => {
        let comp: PrestartCheckResponseDeleteDialogComponent;
        let fixture: ComponentFixture<PrestartCheckResponseDeleteDialogComponent>;
        let service: PrestartCheckResponseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckResponseDeleteDialogComponent],
                providers: [
                    PrestartCheckResponseService
                ]
            })
            .overrideTemplate(PrestartCheckResponseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckResponseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckResponseService);
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
