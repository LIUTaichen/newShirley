/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleDialogComponent } from '../../../../../../main/webapp/app/entities/niggle/niggle-dialog.component';
import { NiggleService } from '../../../../../../main/webapp/app/entities/niggle/niggle.service';
import { Niggle } from '../../../../../../main/webapp/app/entities/niggle/niggle.model';
import { PurchaseOrderService } from '../../../../../../main/webapp/app/entities/purchase-order';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant';
import { MaintenanceContractorService } from '../../../../../../main/webapp/app/entities/maintenance-contractor';

describe('Component Tests', () => {

    describe('Niggle Management Dialog Component', () => {
        let comp: NiggleDialogComponent;
        let fixture: ComponentFixture<NiggleDialogComponent>;
        let service: NiggleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleDialogComponent],
                providers: [
                    PurchaseOrderService,
                    PlantService,
                    MaintenanceContractorService,
                    NiggleService
                ]
            })
            .overrideTemplate(NiggleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Niggle(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.niggle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'niggleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Niggle();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.niggle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'niggleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
