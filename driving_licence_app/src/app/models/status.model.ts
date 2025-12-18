import { DrivingLessons } from './driving-lessons.model';

export class Status {
  constructor(
    public id: number,
    public name: string,
    public drivingLessonList: DrivingLessons[],
    public requestList: Request[]
  ) {}
  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getDrivingLessonList(): DrivingLessons[] {
    return this.drivingLessonList;
  }
  set setDrivingLessonList(value: DrivingLessons[]) {
    this.drivingLessonList = value;
  }

  get getRequestList(): Request[] {
    return this.requestList;
  }
  set setRequestList(value: Request[]) {
    this.requestList = value;
  }
}
