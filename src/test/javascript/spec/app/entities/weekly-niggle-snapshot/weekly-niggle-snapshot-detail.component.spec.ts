/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { WeeklyNiggleSnapshotDetailComponent } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot-detail.component';
import { WeeklyNiggleSnapshotService } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.service';
import { WeeklyNiggleSnapshot } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.model';

describe('Component Tests', () => {

    describe('WeeklyNiggleSnapshot Management Detail Component', () => {
        let comp: WeeklyNiggleSnapshotDetailComponent;
        let fixture: ComponentFixture<WeeklyNiggleSnapshotDetailComponent>;
        let service: WeeklyNiggleSnapshotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [WeeklyNiggleSnapshotDetailComponent],
                providers: [
                    WeeklyNiggleSnapshotService
                ]
            })
            .overrideTemplate(WeeklyNiggleSnapshotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeeklyNiggleSnapshotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeeklyNiggleSnapshotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WeeklyNiggleSnapshot(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.weeklyNiggleSnapshot).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
