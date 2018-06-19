/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { WeeklyNiggleSnapshotDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot-delete-dialog.component';
import { WeeklyNiggleSnapshotService } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.service';

describe('Component Tests', () => {

    describe('WeeklyNiggleSnapshot Management Delete Component', () => {
        let comp: WeeklyNiggleSnapshotDeleteDialogComponent;
        let fixture: ComponentFixture<WeeklyNiggleSnapshotDeleteDialogComponent>;
        let service: WeeklyNiggleSnapshotService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [WeeklyNiggleSnapshotDeleteDialogComponent],
                providers: [
                    WeeklyNiggleSnapshotService
                ]
            })
            .overrideTemplate(WeeklyNiggleSnapshotDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeeklyNiggleSnapshotDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeeklyNiggleSnapshotService);
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
