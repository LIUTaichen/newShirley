import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public company?: string,
        public phone?: string,
        public address?: string,
        public location?: string,
        public webPage?: string,
        public notes?: string,
        public isActive?: boolean,
        public abbreviation?: string,
    ) {
        this.isActive = false;
    }
}
