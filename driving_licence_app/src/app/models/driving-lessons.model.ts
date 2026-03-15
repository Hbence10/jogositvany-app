import { DrivingLicenseCategory } from './driving-license.model';
import { Instructors } from './instructors.model';
import { PaymentMethod } from './payment-method.model';
import { ReservedHour } from './reserved-hour.model';
import { Status } from './status.model';
import { Students } from './students.model';

export class DrivingLessons {
  constructor(
    public id: number,
    public startKm: number,
    public endKm: number,
    public location: string,
    public pickUpPlace: string,
    public dropOffPlace: string,
    public lessonHourNumber: number,
    public isPaid: boolean,
    public isEnd: boolean,
    public drivingLessonStatus: Status,
    public category: DrivingLicenseCategory,
    public paymentMethod: PaymentMethod,
    public reservedHour: ReservedHour,
    public dStudent: Students,
    public dInstructor: Instructors,
    public isCancelled: boolean
  ) {}
}
