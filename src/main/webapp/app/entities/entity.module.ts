import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FleetManagementPlantModule } from './plant/plant.module';
import { FleetManagementOffHireRequestModule } from './off-hire-request/off-hire-request.module';
import { FleetManagementCategoryModule } from './category/category.module';
import { FleetManagementCompanyModule } from './company/company.module';
import { FleetManagementMaintenanceContractorModule } from './maintenance-contractor/maintenance-contractor.module';
import { FleetManagementProjectModule } from './project/project.module';
import { FleetManagementCompetencyModule } from './competency/competency.module';
import { FleetManagementPeopleModule } from './people/people.module';
import { FleetManagementNiggleModule } from './niggle/niggle.module';
import { FleetManagementPlantLogModule } from './plant-log/plant-log.module';
import { FleetManagementPurchaseOrderModule } from './purchase-order/purchase-order.module';
import { FleetManagementLocationModule } from './location/location.module';
import { FleetManagementEmailSubscriptionModule } from './email-subscription/email-subscription.module';
import { FleetManagementNiggleSnapshotModule } from './niggle-snapshot/niggle-snapshot.module';
import { FleetManagementPrestartCheckModule } from './prestart-check/prestart-check.module';
import { FleetManagementPrestartCheckResponseModule } from './prestart-check-response/prestart-check-response.module';
import { FleetManagementPrestartCheckConfigModule } from './prestart-check-config/prestart-check-config.module';
import { FleetManagementPrestartQuestionModule } from './prestart-question/prestart-question.module';
import { FleetManagementPrestartQuestionOptionModule } from './prestart-question-option/prestart-question-option.module';
import { FleetManagementPrestartCheckQuestionListItemModule } from './prestart-check-question-list-item/prestart-check-question-list-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FleetManagementPlantModule,
        FleetManagementOffHireRequestModule,
        FleetManagementCategoryModule,
        FleetManagementCompanyModule,
        FleetManagementMaintenanceContractorModule,
        FleetManagementProjectModule,
        FleetManagementCompetencyModule,
        FleetManagementPeopleModule,
        FleetManagementNiggleModule,
        FleetManagementPlantLogModule,
        FleetManagementPurchaseOrderModule,
        FleetManagementLocationModule,
        FleetManagementEmailSubscriptionModule,
        FleetManagementNiggleSnapshotModule,
        FleetManagementPrestartCheckModule,
        FleetManagementPrestartCheckResponseModule,
        FleetManagementPrestartCheckConfigModule,
        FleetManagementPrestartQuestionModule,
        FleetManagementPrestartQuestionOptionModule,
        FleetManagementPrestartCheckQuestionListItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FleetManagementEntityModule {}
