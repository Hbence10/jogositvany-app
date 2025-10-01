import { DrivingLessons } from "./driving-lessons.model";
import { ReservedDate } from "./reserved-data.model";

export class ReservedHour{
  constructor(
    private id:number,
    private start: number,
    private end: number,
    private drivinglessons: DrivingLessons,
    private reservedDate: ReservedDate,
  ){}
}
