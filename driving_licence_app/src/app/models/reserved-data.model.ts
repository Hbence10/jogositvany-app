import { ReservedHour } from "./reserved-hour.model";

export class ReservedDate {
  constructor(
    private id: number,
    private date: Date,
    private isFull: boolean = false,
    private reservedHourList: ReservedHour[]
  ){}
}
