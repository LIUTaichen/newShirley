import { BaseEntity } from './../../shared';

export class PrestartCheckConfig implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public questionlists?: BaseEntity[],
    ) {
    }
}
