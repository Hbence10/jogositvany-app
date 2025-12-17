import { DrivingLessons } from './driving-lessons.model';
import { ReservedDate } from './reserved-data.model';

export class ReservedHour {
  constructor(
    public id: number,
    public startHour: number,
    public startMin: number,
    public endHour: number,
    public endMin: number,
    public drivingLessons: DrivingLessons,
    public reservedDate: ReservedDate
  ) {}

  get getStartHour(): number {
    return this.startHour;
  }
  set setStartHour(value: number) {
    this.startHour = value;
  }

  get getStartMin(): number {
    return this.startMin;
  }
  set setStartMin(value: number) {
    this.startMin = value;
  }

  get getEndHour(): number {
    return this.endHour;
  }
  set setEndHour(value: number) {
    this.endHour = value;
  }

  get getEndMin(): number {
    return this.endMin;
  }
  set setEndMin(value: number) {
    this.endMin = value;
  }

  get getDrivingLessons(): DrivingLessons {
    return this.drivingLessons;
  }
  set setDrivingLessons(value: DrivingLessons) {
    this.drivingLessons = value;
  }

  get getReservedDate(): ReservedDate {
    return this.reservedDate;
  }
  set setReservedDate(value: ReservedDate) {
    this.reservedDate = value;
  }
}
