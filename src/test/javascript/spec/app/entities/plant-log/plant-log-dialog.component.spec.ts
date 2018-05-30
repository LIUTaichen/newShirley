/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantLogDialogComponent } from '../../../../../../main/webapp/app/entities/plant-log/plant-log-dialog.component';
import { PlantLogService } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.service';
import { PlantLog } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.model';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant';
import { PeopleService } from '../../../../../../main/webapp/app/entities/people';
import { ProjectService } from '../../../../../../main/webapp/app/entities/project';

describe('Component Tests', () => {

    describe('PlantLog Management Dialog Component', () => {
        let comp: PlantLogDialogComponent;
        let fixture: ComponentFixture<PlantLogDialogComponent>;
        let service: PlantLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantLogDialogComponent],
                providers: [
                    PlantService,
                    PeopleService,
                    ProjectService,
                    PlantLogService
                ]
            })
            .overrideTemplate(PlantLogDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantLogDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PlantLog(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.plantLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'plantLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PlantLog();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.plantLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'plantLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
