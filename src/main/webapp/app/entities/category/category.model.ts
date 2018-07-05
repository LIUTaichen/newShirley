import { BaseEntity } from './../../shared';

export const enum MaintenanceGroup {
    'YELLOW_FLEET',
    'WHITE_FLEET'
}

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
        public maintenanceGroup?: MaintenanceGroup,
        public competency?: BaseEntity,
        public prestartCheckConfig?: BaseEntity,
    ) {
        this.trackUsage = false;
        this.isEarchMovingPlant = false;
        this.isTrackedForInternalBilling = false;
    }
}
