import { DrivingLessons } from "./driving-lessons.model";

export class Status {
  constructor(
    private id: number,
    private name: string,
    private drivingLessonList: DrivingLessons[],
    private rewuestList: Request[],
  ) {}
}
