/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantComponent } from '../../../../../../main/webapp/app/entities/plant/plant.component';
import { PlantService } from '../../../../../../main/webapp/app/entities/plant/plant.service';
import { Plant } from '../../../../../../main/webapp/app/entities/plant/plant.model';

describe('Component Tests', () => {

    describe('Plant Management Component', () => {
        let comp: PlantComponent;
        let fixture: ComponentFixture<PlantComponent>;
        let service: PlantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantComponent],
                providers: [
                    PlantService
                ]
            })
            .overrideTemplate(PlantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Plant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.plants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
