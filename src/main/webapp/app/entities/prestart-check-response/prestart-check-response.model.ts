import { BaseEntity } from './../../shared';

export class PrestartCheckResponse implements BaseEntity {
    constructor(
        public id?: number,
        public prestartCheck?: BaseEntity,
        public question?: BaseEntity,
        public response?: BaseEntity,
    ) {
    }
}
