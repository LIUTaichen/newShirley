/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleSnapshotDialogComponent } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot-dialog.component';
import { NiggleSnapshotService } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.service';
import { NiggleSnapshot } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.model';

describe('Component Tests', () => {

    describe('NiggleSnapshot Management Dialog Component', () => {
        let comp: NiggleSnapshotDialogComponent;
        let fixture: ComponentFixture<NiggleSnapshotDialogComponent>;
        let service: NiggleSnapshotService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleSnapshotDialogComponent],
                providers: [
                    NiggleSnapshotService
                ]
            })
            .overrideTemplate(NiggleSnapshotDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleSnapshotDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleSnapshotService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NiggleSnapshot(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.niggleSnapshot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'niggleSnapshotListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NiggleSnapshot();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.niggleSnapshot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'niggleSnapshotListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
