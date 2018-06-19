/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { WeeklyNiggleSnapshotDialogComponent } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot-dialog.component';
import { WeeklyNiggleSnapshotService } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.service';
import { WeeklyNiggleSnapshot } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.model';

describe('Component Tests', () => {

    describe('WeeklyNiggleSnapshot Management Dialog Component', () => {
        let comp: WeeklyNiggleSnapshotDialogComponent;
        let fixture: ComponentFixture<WeeklyNiggleSnapshotDialogComponent>;
        let service: WeeklyNiggleSnapshotService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [WeeklyNiggleSnapshotDialogComponent],
                providers: [
                    WeeklyNiggleSnapshotService
                ]
            })
            .overrideTemplate(WeeklyNiggleSnapshotDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeeklyNiggleSnapshotDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeeklyNiggleSnapshotService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeeklyNiggleSnapshot(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weeklyNiggleSnapshot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weeklyNiggleSnapshotListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeeklyNiggleSnapshot();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weeklyNiggleSnapshot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weeklyNiggleSnapshotListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
