import { Instructors } from './instructors.model';
import { Students } from './students.model';

export class InstructorJoinRequest {
  constructor(
    public id: number,
    public isAccepted: boolean = false,
    public sentAt: Date,
    public instructorJoinRequestStudent: Students,
    public instructorJoinRequestInstructor: Instructors
  ) {}
}
