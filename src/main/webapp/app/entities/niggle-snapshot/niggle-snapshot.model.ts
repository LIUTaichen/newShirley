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

export class NiggleSnapshot implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public status?: Status,
        public priority?: Priority,
        public count?: number,
        public ageOfOldest?: number,
    ) {
    }
}
