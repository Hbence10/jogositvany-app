import { DrivingLessons } from "./driving-lessons.model";

export class Status {
  constructor(
    private id: number,
    private name: string,
    private drivingLessonList: DrivingLessons[],
    private requestList: Request[],
  ) {}
  get getName(): string {
    return this.name;
  }
  set setName(value: string){
    this.name = value;
  }

  get getDrivingLessonList(): DrivingLessons[] {
    return this.drivingLessonList;
  }
  set setDrivingLessonList(value: DrivingLessons[]){
    this.drivingLessonList = value;
  }

  get getRequestList(): Request[] {
    return this.requestList;
  }
  set setRequestList(value: Request[]){
    this.requestList = value;
  }
}
