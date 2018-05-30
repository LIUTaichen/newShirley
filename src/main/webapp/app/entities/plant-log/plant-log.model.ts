import { BaseEntity } from './../../shared';

export class PlantLog implements BaseEntity {
    constructor(
        public id?: number,
        public meterReading?: number,
        public hubboReading?: number,
        public serviceDueAt?: number,
        public rucDueAt?: number,
        public wofDueDate?: any,
        public cofDueDate?: any,
        public serviceDueDate?: any,
        public plant?: BaseEntity,
        public operator?: BaseEntity,
        public site?: BaseEntity,
    ) {
    }
}
