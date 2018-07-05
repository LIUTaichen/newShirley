import { BaseEntity } from './../../shared';

export class PrestartCheckResponse implements BaseEntity {
    constructor(
        public id?: number,
        public question?: BaseEntity,
        public response?: BaseEntity,
        public prestartCheck?: BaseEntity,
    ) {
    }
}
