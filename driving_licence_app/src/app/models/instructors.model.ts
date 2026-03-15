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
}
