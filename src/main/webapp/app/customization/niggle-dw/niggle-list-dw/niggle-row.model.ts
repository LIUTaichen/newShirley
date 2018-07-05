import { Niggle} from '../../../entities/niggle/niggle.model';

export class NiggleRow extends Niggle {
    constructor(
        public priorityOrder?: number,
        public orderNo?: string,
        public dateUpdated?: any,
        public plantNumber?: string,
        public plantDescription?: string,
        public plantCategory?: String,
        public site?: string,
        public location?: string,
        public locationUpdateTime?: any,
        public owner?: string,
        public contractor?: string,
        public daysOpened?: number,
        public googleLink?: String,
        public eta?: Date
    ) {
        super();
    }
}
