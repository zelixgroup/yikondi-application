import { Moment } from 'moment';

export interface IEmergencyAmbulance {
  id?: number;
  name?: string;
  description?: Moment;
  phoneNumber?: string;
}

export class EmergencyAmbulance implements IEmergencyAmbulance {
  constructor(public id?: number, public name?: string, public description?: Moment, public phoneNumber?: string) {}
}
