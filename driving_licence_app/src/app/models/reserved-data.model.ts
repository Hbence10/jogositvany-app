import { ReservedHour } from "./reserved-hour.model";

export class ReservedDate {
  constructor(
    private id: number,
    private date: Date,
    private isFull: boolean = false,
    private reservedHourList: ReservedHour[]
  ){}


  get getDate(): Date {
    return this.date;
  }
  set setDate(value: Date){
    this.date = value;
  }

  get getIsFull(): boolean {
    return this.isFull;
  }
  set setIsFull(value: boolean){
    this.isFull = value;
  }

  get getReservedHourList(): ReservedHour[] {
    return this.reservedHourList;
  }
  set setReservedHourList(value: ReservedHour[]){
    this.reservedHourList = value;
  }
}
