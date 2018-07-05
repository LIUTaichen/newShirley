/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PrestartCheckDialogComponent } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check-dialog.component';
import { PrestartCheckService } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.service';
import { PrestartCheck } from '../../../../../../main/webapp/app/entities/prestart-check/prestart-check.model';
import { ProjectService } from '../../../../../../main/webapp/app/entities/project';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant';
import { LocationService } from '../../../../../../main/webapp/app/entities/location';
import { PeopleService } from '../../../../../../main/webapp/app/entities/people';

describe('Component Tests', () => {

    describe('PrestartCheck Management Dialog Component', () => {
        let comp: PrestartCheckDialogComponent;
        let fixture: ComponentFixture<PrestartCheckDialogComponent>;
        let service: PrestartCheckService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PrestartCheckDialogComponent],
                providers: [
                    ProjectService,
                    PlantService,
                    LocationService,
                    PeopleService,
                    PrestartCheckService
                ]
            })
            .overrideTemplate(PrestartCheckDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestartCheckDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestartCheckService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheck(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheck = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrestartCheck();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.prestartCheck = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'prestartCheckListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
