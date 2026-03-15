import { Instructors } from './instructors.model';
import { OpeningDetails } from './opening-details.model';
import { Review } from './review.model';
import { SchoolJoinRequest } from './school-join-request.model';
import { SchoolCategory } from './schoolCategory.model';
import { Students } from './students.model';
import { User } from './user.model';

export class School {
  constructor(
    public id: number,
    public name: string,
    public email: string,
    public phone: string,
    public country: string,
    public town: string,
    public address: string,
    public promoText: string,
    public bannerImgPath: string,
    public adminList: User[],
    public owner: User,
    public instructorsList: Instructors[],
    public openingDetails: OpeningDetails[],
    public reviewList: Review[],
    public studentsList: Students[],
    public schoolJoinRequestList: SchoolJoinRequest[],
    public licenseCategoryList: SchoolCategory[]
  ) {}
}
