import { Instructors } from './instructors.model';
import { Status } from './status.model';
import { Students } from './students.model';

export class DrivingLessonRequest {
  constructor(
    public id: number,
    public date: Date,
    public startHour: number,
    public startMin: number,
    public endHour: number,
    public endMin: number,
    public dLessonRequestStudent: Students,
    public dLessonInstructor: Instructors,
    public dLessonStatus: Status
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
