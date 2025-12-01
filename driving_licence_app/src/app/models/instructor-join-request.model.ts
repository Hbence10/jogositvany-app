import { Instructors } from './instructors.model';
import { Students } from './students.model';

export class InstructorJoinRequest {
  constructor(
    private id: number,
    private isAccepted: boolean = false,
    private sentAt: Date,
    private instructorJoinRequestStudent: Students,
    private instructorJoinRequestInstructor: Instructors
  ) {}

  get getIsAccepted(): boolean {
    return this.isAccepted;
  }
  set setIsAccepted(value: boolean) {
    this.isAccepted = value;
  }

  get getSentAt(): Date {
    return this.sentAt;
  }
  set setSentAt(value: Date) {
    this.sentAt = value;
  }

  get getInstructorJoinRequestStudent(): Students {
    return this.instructorJoinRequestStudent;
  }
  set setInstructorJoinRequestStudent(value: Students) {
    this.instructorJoinRequestStudent = value;
  }

  get getInstructorJoinRequestInstructor(): Instructors {
    return this.instructorJoinRequestInstructor;
  }
  set setInstructorJoinRequestInstructor(value: Instructors) {
    this.instructorJoinRequestInstructor = value;
  }
}
