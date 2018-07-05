/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckConfigDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config-dialog.component';
import { PrestartCheckConfigService } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.service';
import { PrestartCheckConfig } from '../../../../../../main/webapp/app/entities/prestart-check-config/prestart-check-config.model';

describe('Component Tests', () => {

    describe('PrestartCheckConfig Management Dialog Component', () => {
        let comp: PrestartCheckConfigDialogComponent;
        let fixture: ComponentFixture<PrestartCheckConfigDialogComponent>;
        let service: PrestartCheckConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckConfigDialogComponent],
                providers: [
                    PrestartCheckConfigService
                ]
            })
            .overrideTemplate(PrestartCheckConfigDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckConfigDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckConfigService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckConfig(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckConfig = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckConfigListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheckConfig();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheckConfig = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckConfigListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
