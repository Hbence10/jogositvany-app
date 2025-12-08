import { Instructors } from './instructors.model';
import { School } from './school.model';
import { Students } from './students.model';

export class ExamRequest {
  constructor(
    public id: number,
    public requestedDate: Date,
    public examRequesterInstructor: Instructors,
    public examSchool: School,
    public examStudent: Students
  ) {}

  get getRequestedDate(): Date {
    return this.requestedDate;
  }
  set setRequestedDate(value: Date) {
    this.requestedDate = value;
  }

  get getExamRequesterInstructor(): Instructors {
    return this.examRequesterInstructor;
  }
  set setExamRequesterInstructor(value: Instructors) {
    this.examRequesterInstructor = value;
  }

  get getExamSchool(): School {
    return this.examSchool;
  }
  set setExamSchool(value: School) {
    this.examSchool = value;
  }

  get getExamStudent(): Students {
    return this.examStudent;
  }
  set setExamStudent(value: Students) {
    this.examStudent = value;
  }
}
