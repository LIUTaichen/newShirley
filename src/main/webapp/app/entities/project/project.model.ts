import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public jobNo?: string,
        public name?: string,
        public location?: string,
        public notes?: string,
        public startDate?: any,
        public endDate?: any,
        public isActive?: boolean,
        public isOnHold?: boolean,
        public details?: string,
    ) {
        this.isActive = false;
        this.isOnHold = false;
    }
}
