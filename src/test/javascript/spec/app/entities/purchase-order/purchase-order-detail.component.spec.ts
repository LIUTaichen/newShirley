/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { PurchaseOrderDetailComponent } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order-detail.component';
import { PurchaseOrderService } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order.service';
import { PurchaseOrder } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order.model';

describe('Component Tests', () => {

    describe('PurchaseOrder Management Detail Component', () => {
        let comp: PurchaseOrderDetailComponent;
        let fixture: ComponentFixture<PurchaseOrderDetailComponent>;
        let service: PurchaseOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [PurchaseOrderDetailComponent],
                providers: [
                    PurchaseOrderService
                ]
            })
            .overrideTemplate(PurchaseOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PurchaseOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PurchaseOrder(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.purchaseOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
