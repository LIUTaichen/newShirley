/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { MaintenanceContractorDetailComponent } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor-detail.component';
import { MaintenanceContractorService } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.service';
import { MaintenanceContractor } from '../../../../../../main/webapp/app/entities/maintenance-contractor/maintenance-contractor.model';

describe('Component Tests', () => {

    describe('MaintenanceContractor Management Detail Component', () => {
        let comp: MaintenanceContractorDetailComponent;
        let fixture: ComponentFixture<MaintenanceContractorDetailComponent>;
        let service: MaintenanceContractorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [MaintenanceContractorDetailComponent],
                providers: [
                    MaintenanceContractorService
                ]
            })
            .overrideTemplate(MaintenanceContractorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaintenanceContractorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaintenanceContractorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MaintenanceContractor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.maintenanceContractor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
