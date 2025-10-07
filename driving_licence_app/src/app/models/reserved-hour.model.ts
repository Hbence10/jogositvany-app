import { DrivingLessons } from "./driving-lessons.model";
import { ReservedDate } from "./reserved-data.model";

export class ReservedHour{
  constructor(
    private id:number,
    private start: number,
    private end: number,
    private drivingLessons: DrivingLessons,
    private reservedDate: ReservedDate,
  ){}

  get getStart(): number {
    return this.start;
  }
  set setStart(value: number){
    this.start = value;
  }

  get getEnd(): number {
    return this.end;
  }
  set setEnd(value: number){
    this.end = value;
  }

  get getDrivingLessons(): DrivingLessons {
    return this.drivingLessons;
  }
  set setDrivingLessons(value: DrivingLessons){
    this.drivingLessons = value;
  }

  get getReservedDate(): ReservedDate {
    return this.reservedDate;
  }
  set setReservedDate(value: ReservedDate){
    this.reservedDate = value;
  }
}
