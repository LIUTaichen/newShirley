/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantDialogComponent } from '../../../../../../main/webapp/app/entities/plant/plant-dialog.component';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant/plant.service';
import { Plant } from '../../../../../../main/webapp/app/entities/plant/plant.model';
import { LocationService } from '../../../../../../main/webapp/app/entities/location';
import { CategoryService } from '../../../../../../main/webapp/app/entities/category';
import { CompanyService } from '../../../../../../main/webapp/app/entities/company';
import { MaintenanceContractorService } from '../../../../../../main/webapp/app/entities/maintenance-contractor';
import { ProjectService } from '../../../../../../main/webapp/app/entities/project';

describe('Component Tests', () => {

    describe('Plant Management Dialog Component', () => {
        let comp: PlantDialogComponent;
        let fixture: ComponentFixture<PlantDialogComponent>;
        let service: PlantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantDialogComponent],
                providers: [
                    LocationService,
                    CategoryService,
                    CompanyService,
                    MaintenanceContractorService,
                    ProjectService,
                    PlantService
                ]
            })
            .overrideTemplate(PlantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Plant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.plant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'plantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Plant();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.plant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'plantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
