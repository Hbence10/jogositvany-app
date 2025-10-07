import { DrivingLessons } from "./driving-lessons.model";

export class DrivingLicenseCategory {
  constructor(
    private id: number,
    private name: string,
    private minAge: number,
    private drivingLessonsList: DrivingLessons[],

  ){}

  get getName(): string {
    return this.name;
  }
  set setName(value: string){
    this.name = value;
  }

  get getMinAge(): number {
    return this.minAge;
  }
  set setMinAge(value: number){
    this.minAge = value;
  }

  get getDrivingLessonsList(): DrivingLessons[] {
    return this.drivingLessonsList;
  }
  set setDrivingLessonsList(value: DrivingLessons[]){
    this.drivingLessonsList = value;
  }

}
