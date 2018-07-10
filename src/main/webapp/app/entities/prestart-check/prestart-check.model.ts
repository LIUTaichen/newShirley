import { BaseEntity } from './../../shared';

export class PrestartCheck implements BaseEntity {
    constructor(
        public id?: number,
        public signatureContentType?: string,
        public signature?: any,
        public plantLog?: BaseEntity,
        public responses?: BaseEntity[],
        public project?: BaseEntity,
        public plant?: BaseEntity,
        public location?: BaseEntity,
        public operator?: BaseEntity,
    ) {
    }
}
