import { Instructors } from "./instructors.model";
import { Status } from "./status.model";
import { Students } from "./students.model";

export class DrivingLessonRequest {
  constructor(
    private id:number,
    private date:Date,
    private startHour:number,
    private startMin:number,
    private endHour:number,
    private endMin:number,
    private dLessonRequestStudent:Students,
    private dLessonInstructor:Instructors,
    private dLessonStatus:Status,
  ){}
}
