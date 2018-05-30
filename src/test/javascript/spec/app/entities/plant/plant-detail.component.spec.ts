/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantDetailComponent } from '../../../../../../main/webapp/app/entities/plant/plant-detail.component';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant/plant.service';
import { Plant } from '../../../../../../main/webapp/app/entities/plant/plant.model';

describe('Component Tests', () => {

    describe('Plant Management Detail Component', () => {
        let comp: PlantDetailComponent;
        let fixture: ComponentFixture<PlantDetailComponent>;
        let service: PlantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantDetailComponent],
                providers: [
                    PlantService
                ]
            })
            .overrideTemplate(PlantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Plant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.plant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
