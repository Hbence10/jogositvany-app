import { DrivingLessons } from "./driving-lessons.model";

export class DrivingLicenseCategory {
  constructor(
    private id: number,
    private name: string,
    private minAge: number,
    private drivingLessonsList: DrivingLessons[],

  ){}
}
