import { Instructors } from './instructors.model';
import { Status } from './status.model';
import { Students } from './students.model';

export class DrivingLessonRequest {
  constructor(
    private id: number,
    private date: Date,
    private startHour: number,
    private startMin: number,
    private endHour: number,
    private endMin: number,
    private dLessonRequestStudent: Students,
    private dLessonInstructor: Instructors,
    private dLessonStatus: Status
  ) {}

  get getDate(): Date {
    return this.date;
  }
  set setDate(value: Date) {
    this.date = value;
  }

  get getStartHour(): number {
    return this.startHour;
  }
  set setStartHour(value: number) {
    this.startHour = value;
  }

  get getStartMin(): number {
    return this.startMin;
  }
  set setStartMin(value: number) {
    this.startMin = value;
  }

  get getEndHour(): number {
    return this.endHour;
  }
  set setEndHour(value: number) {
    this.endHour = value;
  }

  get getEndMin(): number {
    return this.endMin;
  }
  set setEndMin(value: number) {
    this.endMin = value;
  }

  get getDLessonRequestStudent(): Students {
    return this.dLessonRequestStudent;
  }
  set setDLessonRequestStudent(value: Students) {
    this.dLessonRequestStudent = value;
  }

  get getDLessonInstructor(): Instructors {
    return this.dLessonInstructor;
  }
  set setDLessonInstructor(value: Instructors) {
    this.dLessonInstructor = value;
  }

  get getDLessonStatus(): Status {
    return this.dLessonStatus;
  }
  set setDLessonStatus(value: Status) {
    this.dLessonStatus = value;
  }
}
