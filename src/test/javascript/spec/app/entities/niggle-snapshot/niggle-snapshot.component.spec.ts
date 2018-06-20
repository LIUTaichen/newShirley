/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { NiggleSnapshotComponent } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.component';
import { NiggleSnapshotService } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.service';
import { NiggleSnapshot } from '../../../../../../main/webapp/app/entities/niggle-snapshot/niggle-snapshot.model';

describe('Component Tests', () => {

    describe('NiggleSnapshot Management Component', () => {
        let comp: NiggleSnapshotComponent;
        let fixture: ComponentFixture<NiggleSnapshotComponent>;
        let service: NiggleSnapshotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [NiggleSnapshotComponent],
                providers: [
                    NiggleSnapshotService
                ]
            })
            .overrideTemplate(NiggleSnapshotComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NiggleSnapshotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NiggleSnapshotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NiggleSnapshot(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.niggleSnapshots[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
