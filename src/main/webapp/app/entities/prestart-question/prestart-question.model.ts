import { BaseEntity } from './../../shared';

export class PrestartQuestion implements BaseEntity {
    constructor(
        public id?: number,
        public body?: string,
        public isLockOutRequired?: boolean,
        public options?: BaseEntity[],
    ) {
        this.isLockOutRequired = false;
    }
}
