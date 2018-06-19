/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { WeeklyNiggleSnapshotComponent } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.component';
import { WeeklyNiggleSnapshotService } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.service';
import { WeeklyNiggleSnapshot } from '../../../../../../main/webapp/app/entities/weekly-niggle-snapshot/weekly-niggle-snapshot.model';

describe('Component Tests', () => {

    describe('WeeklyNiggleSnapshot Management Component', () => {
        let comp: WeeklyNiggleSnapshotComponent;
        let fixture: ComponentFixture<WeeklyNiggleSnapshotComponent>;
        let service: WeeklyNiggleSnapshotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [WeeklyNiggleSnapshotComponent],
                providers: [
                    WeeklyNiggleSnapshotService
                ]
            })
            .overrideTemplate(WeeklyNiggleSnapshotComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeeklyNiggleSnapshotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeeklyNiggleSnapshotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WeeklyNiggleSnapshot(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.weeklyNiggleSnapshots[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
