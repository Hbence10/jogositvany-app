import { Instructors } from "./instructors.model";
import { School } from "./school.model";
import { Students } from "./students.model";

export class ExamRequest{
  constructor(
    private id: number,
    private requestedDate: Date,
    private examRequesterInstructor: Instructors,
    private examSchool: School,
    private examStudent: Students
  ){}
}
