import { BaseEntity } from './../../shared';

export class PurchaseOrder implements BaseEntity {
    constructor(
        public id?: number,
        public orderNumber?: string,
    ) {
    }
}
