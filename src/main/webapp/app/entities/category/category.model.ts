import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public description?: string,
        public type?: string,
        public trackUsage?: boolean,
        public dailyRate?: number,
        public loadCapacity?: number,
        public hourlyRate?: number,
        public isEarchMovingPlant?: boolean,
        public isTrackedForInternalBilling?: boolean,
        public competency?: BaseEntity,
    ) {
        this.trackUsage = false;
        this.isEarchMovingPlant = false;
        this.isTrackedForInternalBilling = false;
    }
}
