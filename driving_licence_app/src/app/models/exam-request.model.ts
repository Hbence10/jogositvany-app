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
}
