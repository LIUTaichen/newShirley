/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { PlantLogComponent } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.component';
import { PlantLogService } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.service';
import { PlantLog } from '../../../../../../main/webapp/app/entities/plant-log/plant-log.model';

describe('Component Tests', () => {

    describe('PlantLog Management Component', () => {
        let comp: PlantLogComponent;
        let fixture: ComponentFixture<PlantLogComponent>;
        let service: PlantLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PlantLogComponent],
                providers: [
                    PlantLogService
                ]
            })
            .overrideTemplate(PlantLogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlantLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlantLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PlantLog(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.plantLogs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
