import { BaseEntity } from './../../shared';

export class PrestartQuestionOption implements BaseEntity {
    constructor(
        public id?: number,
        public body?: string,
        public isNormal?: boolean,
        public isActive?: boolean,
        public question?: BaseEntity,
    ) {
        this.isNormal = false;
        this.isActive = false;
    }
}
