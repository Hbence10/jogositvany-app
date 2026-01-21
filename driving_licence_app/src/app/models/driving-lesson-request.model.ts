import { Instructors } from './instructors.model';
import { Students } from './students.model';

export class DrivingLessonRequest {
  constructor(
    public id: number | null,
    public date: Date,
    public msg: string,
    public startTime: Date,
    public endTime: Date,
    public dLessonRequestStudent: Students,
    public dLessonInstructor: Instructors,
    public sentAt: Date = new Date(),
    public isAccepted: boolean | null = null,
    public acceptedAt: Date | null = null,
  ) {}

  get getDate(): Date {
    return this.date;
  }
  set setDate(value: Date) {
    this.date = value;
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
}
