import { ReservedHour } from './reserved-hour.model';

export class ReservedDate {
  constructor(
    public id: number,
    public date: Date,
    public isFull: boolean = false,
    public reservedHourList: ReservedHour[]
  ) {}
}
