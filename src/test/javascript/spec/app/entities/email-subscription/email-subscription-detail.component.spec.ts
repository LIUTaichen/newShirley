/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FleetManagementTestModule } from '../../../test.module';
import { EmailSubscriptionDetailComponent } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription-detail.component';
import { EmailSubscriptionService } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription.service';
import { EmailSubscription } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription.model';

describe('Component Tests', () => {

    describe('EmailSubscription Management Detail Component', () => {
        let comp: EmailSubscriptionDetailComponent;
        let fixture: ComponentFixture<EmailSubscriptionDetailComponent>;
        let service: EmailSubscriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [EmailSubscriptionDetailComponent],
                providers: [
                    EmailSubscriptionService
                ]
            })
            .overrideTemplate(EmailSubscriptionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailSubscriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailSubscriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmailSubscription(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.emailSubscription).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
