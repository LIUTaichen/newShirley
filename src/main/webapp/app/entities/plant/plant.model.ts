import { BaseEntity } from './../../shared';

export const enum MeterUnit {
    'KM',
    'HOUR'
}

export const enum HireStatus {
    'ONHIRE',
    'OFFHIRE'
}

export class Plant implements BaseEntity {
    constructor(
        public id?: number,
        public fleetId?: string,
        public name?: string,
        public notes?: string,
        public purchaseDate?: any,
        public isActive?: boolean,
        public description?: string,
        public vin?: string,
        public rego?: string,
        public dateOfManufacture?: any,
        public imageContentType?: string,
        public image?: any,
        public tankSize?: number,
        public maintenanceDueAt?: number,
        public meterUnit?: MeterUnit,
        public certificateDueDate?: any,
        public rucDueAtKm?: number,
        public hubboReading?: number,
        public loadCapacity?: number,
        public hourlyRate?: number,
        public registrationDueDate?: any,
        public hireStatus?: HireStatus,
        public gpsDeviceSerial?: string,
        public location?: BaseEntity,
        public category?: BaseEntity,
        public owner?: BaseEntity,
        public assignedContractor?: BaseEntity,
    ) {
        this.isActive = false;
    }
}
