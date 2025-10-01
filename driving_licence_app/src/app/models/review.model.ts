import { Instructors } from "./instructors.model";
import { School } from "./school.model";
import { Students } from "./students.model";

export class Review{
  constructor(
    private id: number,
    private text: string,
    private createdAt: Date,
    private rating: number,
    private reviewAuthor: Students,
    private aboutInstructor: Instructors,
    private aboutSchool: School,
  ){}
}
