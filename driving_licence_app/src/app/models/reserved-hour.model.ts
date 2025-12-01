import { DrivingLessons } from "./driving-lessons.model";
import { ReservedDate } from "./reserved-data.model";

export class ReservedHour{
  constructor(
    private id:number,
    private startHour: number,
    private startMin: number,
    private endHour: number,
    private endMin: number,
    private drivingLessons: DrivingLessons,
    private reservedDate: ReservedDate,
  ){}


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
