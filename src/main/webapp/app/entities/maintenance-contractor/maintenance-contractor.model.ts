import { BaseEntity } from './../../shared';

export class MaintenanceContractor implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
