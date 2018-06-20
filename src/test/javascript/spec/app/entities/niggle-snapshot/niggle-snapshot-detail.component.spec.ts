/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleSnapshotDetailComponent } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot-detail.component';
import { NiggleSnapshotService } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.service';
import { NiggleSnapshot } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.model';

describe('Component Tests', () => {

    describe('NiggleSnapshot Management Detail Component', () => {
        let comp: NiggleSnapshotDetailComponent;
        let fixture: ComponentFixture<NiggleSnapshotDetailComponent>;
        let service: NiggleSnapshotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleSnapshotDetailComponent],
                providers: [
                    NiggleSnapshotService
                ]
            })
            .overrideTemplate(NiggleSnapshotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleSnapshotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleSnapshotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NiggleSnapshot(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.niggleSnapshot).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
