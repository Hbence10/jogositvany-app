import { DrivingLessons } from './driving-lessons.model';

export class PaymentMethod {
  constructor(
    private id: number,
    private name: string,
    private drivingLessonsList: DrivingLessons[]
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
