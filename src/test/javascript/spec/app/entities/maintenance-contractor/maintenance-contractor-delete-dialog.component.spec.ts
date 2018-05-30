/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { MaintenanceContractorDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor-delete-dialog.component';
import { MaintenanceContractorService } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.service';

describe('Component Tests', () => {

    describe('MaintenanceContractor Management Delete Component', () => {
        let comp: MaintenanceContractorDeleteDialogComponent;
        let fixture: ComponentFixture<MaintenanceContractorDeleteDialogComponent>;
        let service: MaintenanceContractorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [MaintenanceContractorDeleteDialogComponent],
                providers: [
                    MaintenanceContractorService
                ]
            })
            .overrideTemplate(MaintenanceContractorDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaintenanceContractorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaintenanceContractorService);
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
