import { Instructors } from "./instructors.model";
import { Students } from "./students.model";

export class InstructorJoinRequest{
  constructor(
    private id:number,
    private isAccepted:boolean =false,
    private sentAt: Date,
    private instructorJoinRequestStudent:Students,
    private instructorJoinRequestInstructor:Instructors,
  ){}
}
