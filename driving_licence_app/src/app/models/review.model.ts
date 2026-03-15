import { Instructors } from './instructors.model';
import { School } from './school.model';
import { Students } from './students.model';

export class Review {
  constructor(
    public id?: number | null,
    public text?: string,
    public createdAt?: Date,
    public rating?: number,
    public isAnonymous: boolean = false,
    public reviewAuthor?: Students,
    public aboutInstructor?: Instructors,
    public aboutSchool?: School
  ) {}
}
