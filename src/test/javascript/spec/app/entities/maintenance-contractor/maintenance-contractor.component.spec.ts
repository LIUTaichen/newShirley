/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { MaintenanceContractorComponent } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.component';
import { MaintenanceContractorService } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.service';
import { MaintenanceContractor } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.model';

describe('Component Tests', () => {

    describe('MaintenanceContractor Management Component', () => {
        let comp: MaintenanceContractorComponent;
        let fixture: ComponentFixture<MaintenanceContractorComponent>;
        let service: MaintenanceContractorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [MaintenanceContractorComponent],
                providers: [
                    MaintenanceContractorService
                ]
            })
            .overrideTemplate(MaintenanceContractorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaintenanceContractorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaintenanceContractorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MaintenanceContractor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.maintenanceContractors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
