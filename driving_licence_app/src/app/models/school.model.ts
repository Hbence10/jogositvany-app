import { Instructors } from "./instructors.model";
import { OpeningDetails } from "./opening-details.model";
import { Review } from "./review.model";
import { Students } from "./students.model";
import { User } from "./user.model";

export class School {
  constructor(
    private id: number,
    private name: string,
    private email: string,
    private phone:string,
    private country: string,
    private town: string,
    private address: string,
    private promoText: string,
    private bannerImgPath: string,
    private administrator: User,
    private instructorsList: Instructors[],
    private openingDetails: OpeningDetails[],
    private reviewList: Review[],
    private studentsList: Students[]
  ){}
}
