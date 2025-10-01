import { DrivingLessons } from "./driving-lessons.model";

export class PaymentMethod {
  constructor(
    private id: number,
    private name: string,
    private drivingLessonsList: DrivingLessons[]
  ){}
}
