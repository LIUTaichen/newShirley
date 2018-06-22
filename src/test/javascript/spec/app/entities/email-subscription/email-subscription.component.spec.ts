/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FleetManagementTestModule } from '../../../test.module';
import { EmailSubscriptionComponent } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription.component';
import { EmailSubscriptionService } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription.service';
import { EmailSubscription } from '../../../../../../main/webapp/app/entities/email-subscription/email-subscription.model';

describe('Component Tests', () => {

    describe('EmailSubscription Management Component', () => {
        let comp: EmailSubscriptionComponent;
        let fixture: ComponentFixture<EmailSubscriptionComponent>;
        let service: EmailSubscriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FleetManagementTestModule],
                declarations: [EmailSubscriptionComponent],
                providers: [
                    EmailSubscriptionService
                ]
            })
            .overrideTemplate(EmailSubscriptionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailSubscriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailSubscriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmailSubscription(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.emailSubscriptions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
