import { Status, Priority} from '../../../entities/niggle/niggle.model';

export class NiggleRow {
    constructor(
        public id?: number,
        public description?: string,
        public status?: Status,
        public note?: string,
        public priority?: Priority,
        public priorityOrder?: number,
        public quattraReference?: string,
        public quattraComments?: string,
        public auditNo?: string,
        public dateOpened?: any,
        public dateUpdated?: any,
        public dateClosed?: any,
        public plantNumber?: string,
        public plantDescription?: string,
        public site?: string,
        public location?: string,
        public locationUpdateTime?: any,
        public owner?: string,
        public contractor?: string,
        public daysOpened?: number,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any
    ) {
    }
}
