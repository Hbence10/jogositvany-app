import { DrivingLessons } from "./driving-lessons.model";
import { DrivingLicenseCategory } from "./driving-license.model";
import { School } from "./school.model";

export class DrivingLessonType {
  constructor(
    private id:number,
    private name:string,
    private price:number,
    private instructorDrivingLessons:DrivingLessons[],
    private dirivingLicenseCategory:DrivingLicenseCategory,
    private drivingTypeSchool:School,
  ){}
}
