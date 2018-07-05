import { BaseEntity } from './../../shared';

export class PrestartCheckQuestionListItem implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public prestartCheckConfig?: BaseEntity,
        public question?: BaseEntity,
    ) {
    }
}
