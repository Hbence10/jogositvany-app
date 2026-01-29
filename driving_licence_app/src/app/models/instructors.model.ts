import { DrivingLessons } from './driving-lessons.model';
import { DrivingLicenseCategory } from './driving-license.model';
import { Review } from './review.model';
import { School } from './school.model';
import { Students } from './students.model';
import { User } from './user.model';
import { Vehicle } from './vehicle.model';

export class Instructors {
  constructor(
    public id: number,
    public promoText: string,
    public instructorUser: User,
    public instructorSchool: School,
    public vehicle: Vehicle,
    public reviewList: Review[],
    public students: Students[],
    public instructorDrivingLessons: DrivingLessons[],
    public categoryList: DrivingLicenseCategory[]
  ) {}

  get getPromoText(): string {
    return this.promoText;
  }
  set setPromoText(value: string) {
    this.promoText = value;
  }

  get getInstructorUser(): User {
    return this.instructorUser;
  }
  set setInstructorUser(value: User) {
    this.instructorUser = value;
  }

  get getInstructorSchool(): School {
    return this.instructorSchool;
  }
  set setInstructorSchool(value: School) {
    this.instructorSchool = value;
  }

  get getVehicle(): Vehicle {
    return this.vehicle;
  }
  set setVehicle(value: Vehicle) {
    this.vehicle = value;
  }

  get getReviewList(): Review[] {
    return this.reviewList;
  }
  set setReviewList(value: Review[]) {
    this.reviewList = value;
  }

  get getStudents(): Students[] {
    return this.students;
  }
  set setStudents(value: Students[]) {
    this.students = value;
  }

  get getInstructorDrivingLessons(): DrivingLessons[] {
    return this.instructorDrivingLessons;
  }
  set setInstructorDrivingLessons(value: DrivingLessons[]) {
    this.instructorDrivingLessons = value;
  }
}
