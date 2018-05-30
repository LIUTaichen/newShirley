import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public latitude?: number,
        public longitude?: number,
        public address?: string,
        public bearing?: number,
        public direction?: string,
        public speed?: number,
        public timestamp?: any,
        public provider?: string,
        public project?: BaseEntity,
    ) {
    }
}
