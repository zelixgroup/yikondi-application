import { Moment } from 'moment';

export interface IHoliday {
  id?: number;
  label?: string;
  correspondingDate?: Moment;
}

export class Holiday implements IHoliday {
  constructor(public id?: number, public label?: string, public correspondingDate?: Moment) {}
}
