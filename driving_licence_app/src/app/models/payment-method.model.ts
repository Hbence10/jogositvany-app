import { DrivingLessons } from './driving-lessons.model';

export class PaymentMethod {
  constructor(
    public id: number,
    public name: string,
    public drivingLessonsList: DrivingLessons[]
  ) {}

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getDrivingLessonsList(): DrivingLessons[] {
    return this.drivingLessonsList;
  }
  set setDrivingLessonsList(value: DrivingLessons[]) {
    this.drivingLessonsList = value;
  }
}
