import { BaseEntity } from './../../shared';

export class People implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public phone?: string,
        public name?: string,
    ) {
    }
}
