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

  get getName(): string {
    return this.name;
  }
  set setName(value: string){
    this.name = value;
  }

  get getEmail(): string {
    return this.email;
  }
  set setEmail(value: string){
    this.email = value;
  }

  get getPhone(): string {
    return this.phone;
  }
  set setPhone(value: string){
    this.phone = value;
  }

  get getCountry(): string {
    return this.country;
  }
  set setCountry(value: string){
    this.country = value;
  }

  get getTown(): string {
    return this.town;
  }
  set setTown(value: string) {
    this.town = value;
  }

  get getAddress(): string {
    return this.address;
  }
  set setAddress(value: string) {
    this.address = value;
  }

  get getPromoText(): string {
    return this.promoText;
  }
  set setPromoText(value: string) {
    this.promoText = value;
  }

  get getBannerImgPath(): string {
    return this.bannerImgPath;
  }
  set setBannerImgPath(value: string) {
    this.bannerImgPath = value;
  }

  get getAdministrator(): User {
    return this.administrator;
  }
  set setAdministrator(value: User) {
    this.administrator = value;
  }

  get getInstructorsList(): Instructors[] {
    return this.instructorsList;
  }
  set setInstructorsList(value: Instructors[]){
    this.instructorsList = value;
  }

  get getOpeningDetails(): OpeningDetails[] {
    return this.openingDetails;
  }
  set setOpeningDetails(value: OpeningDetails[]){
    this.openingDetails = value;
  }

  get getReviewList(): Review[] {
    return this.reviewList;
  }
  set setReviewList(value: Review[]){
    this.reviewList = value;
  }

  get getStudentsList(): Students[] {
    return this.studentsList;
  }
  set setStudentsList(value: Students[]){
    this.studentsList = value;
  }
}
