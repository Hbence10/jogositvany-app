import { DrivingLessons } from './driving-lessons.model';

export class PaymentMethod {
  constructor(
    public id: number,
    public name: string,
    public drivingLessonsList: DrivingLessons[]
  ) {}
}
