import { DrivingLessons } from './driving-lessons.model';
import { DrivingLicenseCategory } from './driving-license.model';
import { School } from './school.model';

export class DrivingLessonType {
  constructor(
    public id: number,
    public name: string,
    public price: number,
    public instructorDrivingLessons: DrivingLessons[],
    public dirivingLicenseCategory: DrivingLicenseCategory,
    public drivingTypeSchool: School
  ) {}

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getPrice(): number {
    return this.price;
  }
  set setPrice(value: number) {
    this.price = value;
  }

  get getInstructorDrivingLessons(): DrivingLessons[] {
    return this.instructorDrivingLessons;
  }
  set setInstructorDrivingLessons(value: DrivingLessons[]) {
    this.instructorDrivingLessons = value;
  }

  get getDrivingLicenseCategory(): DrivingLicenseCategory {
    return this.dirivingLicenseCategory;
  }
  set setDrivingLicenseCategory(value: DrivingLicenseCategory) {
    this.dirivingLicenseCategory = value;
  }

  get getDrivingTypeSchool(): School {
    return this.drivingTypeSchool;
  }
  set setDrivingTypeSchool(value: School) {
    this.drivingTypeSchool = value;
  }
}
