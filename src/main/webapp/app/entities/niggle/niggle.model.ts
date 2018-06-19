import { BaseEntity } from './../../shared';

export const enum Status {
    'SUBMITTED',
    'OPEN',
    'WINTER_WORK',
    'IN_PROGRESS',
    'ON_HOLD',
    'COMPLETED',
    'CLOSED',
    'TBR'
}

export const enum Priority {
    'LOW',
    'MEDIUM',
    'HIGH'
}

export class Niggle implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public status?: Status,
        public note?: string,
        public priority?: Priority,
        public quattraReference?: string,
        public quattraComments?: string,
        public invoiceNo?: string,
        public dateOpened?: any,
        public dateClosed?: any,
        public dateCompleted?: any,
        public eta?: any,
        public purchaseOrder?: BaseEntity,
        public plant?: BaseEntity,
        public assignedContractor?: BaseEntity,
    ) {
    }
}
