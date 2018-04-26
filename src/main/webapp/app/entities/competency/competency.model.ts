import { BaseEntity } from './../../shared';

export class Competency implements BaseEntity {
    constructor(
        public id?: number,
        public competency?: string,
        public grouping?: string,
        public sortOrder?: number,
    ) {
    }
}
