/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantLogDetailComponent } from '../../../../../../main/webapp/app/entities/plant-log/plant-log-detail.component';
import { PlantLogService } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.service';
import { PlantLog } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.model';

describe('Component Tests', () => {

    describe('PlantLog Management Detail Component', () => {
        let comp: PlantLogDetailComponent;
        let fixture: ComponentFixture<PlantLogDetailComponent>;
        let service: PlantLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantLogDetailComponent],
                providers: [
                    PlantLogService
                ]
            })
            .overrideTemplate(PlantLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PlantLog(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.plantLog).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
