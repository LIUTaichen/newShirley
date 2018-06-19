import { BaseEntity } from './../../shared';

export const enum Event {
    'ON_HOLD',
    'HIGH_PRIORITY'
}

export const enum RecepientType {
    'TO',
    'CC',
    'BCC'
}

export class EmailSubscription implements BaseEntity {
    constructor(
        public id?: number,
        public event?: Event,
        public email?: string,
        public recipientType?: RecepientType,
    ) {
    }
}
