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


  get getStartKm(): number {
    return this.startKm;
  }
  set setStartKm(value: number) {
    this.startKm = value;
  }

  get getEndKm(): number {
    return this.endKm;
  }
  set setEndKm(value: number){
    this.endKm = value;
  }

  get getLocation(): string {
    return this.location;
  }
  set setLocation(value: string) {
    this.location = value;
  }

  get getPickUpPlace(): string {
    return this.pickUpPlace;
  }
  set setPickUpPlace(value: string){
    this.pickUpPlace = value;
  }

  get getDropOffPlace(): string {
    return this.dropOffPlace;
  }
  set setDropOffPlace(value: string){
    this.dropOffPlace = value;
  }

  get getLessonHourNumber(): number {
    return this.lessonHourNumber;
  }
  set setLessonHourNumber(value: number){
    this.lessonHourNumber = value;
  }

  get getIsPaid(): boolean {
    return this.isPaid;
  }
  set setIsPaid(value: boolean){
    this.isPaid = value;
  }

  get getDrivingLessonStatus(): Status {
    return this.drivingLessonStatus;
  }
  set setDrivingLessonStatus(value: Status){
    this.drivingLessonStatus = value;
  }

  get getCategory(): DrivingLicenseCategory {
    return this.category;
  }
  set setCategory(value: DrivingLicenseCategory){
    this.category = value;
  }

  get getPaymentMethod(): PaymentMethod {
    return this.paymentMethod;
  }
  set setPaymentMethod(value: PaymentMethod){
    this.paymentMethod = value;
  }

  get getReservedHour(): ReservedHour {
    return this.reservedHour;
  }
  set setReservedHour(value: ReservedHour) {
    this.reservedHour = value;
  }

  get getStudent(): Students {
    return this.dStudent;
  }
  set setStudent(value: Students){
    this.dStudent = value;
  }

  get getInstructor(): Instructors {
    return this.dInstructor;
  }
  set setInstructor(value: Instructors) {
    this.dInstructor = value;
  }

}
