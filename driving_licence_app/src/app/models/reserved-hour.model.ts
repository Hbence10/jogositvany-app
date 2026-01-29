import { DrivingLessons } from './driving-lessons.model';
import { ReservedDate } from './reserved-data.model';

export class ReservedHour {
  constructor(
    public id: number,
    public startTime: Date,
    public endTime: Date,

  ) {}

}
