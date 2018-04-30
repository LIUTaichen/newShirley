import { BaseEntity } from './../../shared';

export const enum Status {
    'SUBMITTED',
    'OPEN',
    'ASSIGNED',
    'COMPLETED',
    'CLOSED'
}

export const enum Priority {
    'LEVEL1',
    'LEVEL2',
    'LEVEL3',
    'LEVEL4',
    'LEVEL5'
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
        public dateOpened?: any,
        public dateUpdated?: any,
        public dateClosed?: any,
        public plant?: BaseEntity,
        public assignedContractor?: BaseEntity,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,


    ) {
    }
}
