import { BaseEntity } from './../../shared';

export const enum Status {
    'SUBMITTED',
    'OPEN',
    'ASSIGNED',
    'IN_PROGRESS',
    'ON_HOLD',
    'COMPLETED',
    'CLOSED',
    'TBR'
}

export enum Priority {
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
        public purchaseOrder?: BaseEntity,
        public plant?: BaseEntity,
        public assignedContractor?: BaseEntity,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any
    ) {
    }
}
