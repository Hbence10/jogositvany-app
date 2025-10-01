import { DrivingLicenseCategory } from "./driving-license.model";
import { Instructors } from "./instructors.model";
import { PaymentMethod } from "./payment-method.model";
import { ReservedHour } from "./reserved-hour.model";
import { Status } from "./status.model";
import { Students } from "./students.model";

export class DrivingLessons {
  constructor(
    private id: number,
    private startKm: number,
    private endKm: number,
    private location: string,
    private pickUpPlace: string,
    private dropOffPlace: string,
    private lessonHourNumber: number,
    private isPaid: boolean,
    private drivingLessonStatus: Status,
    private category: DrivingLicenseCategory,
    private paymentMethod: PaymentMethod,
    private reservedHour: ReservedHour,
    private dStudent: Students,
    private dInstructor: Instructors,
  ){}

}
