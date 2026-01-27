import { ExamRequest } from './exam-request.model';
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
    public examRequestList: ExamRequest[],
    public schoolJoinRequestList: SchoolJoinRequest[],
    public licenseCategoryList: SchoolCategory[]
  ) {}

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getEmail(): string {
    return this.email;
  }
  set setEmail(value: string) {
    this.email = value;
  }

  get getPhone(): string {
    return this.phone;
  }
  set setPhone(value: string) {
    this.phone = value;
  }

  get getCountry(): string {
    return this.country;
  }
  set setCountry(value: string) {
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

  get getAdminList(): User[] {
    return this.adminList;
  }
  set setAdminList(value: User[]) {
    this.adminList = value;
  }

  get getOwner(): User {
    return this.owner;
  }
  set setOwner(value: User) {
    this.owner = value;
  }

  get getInstructorsList(): Instructors[] {
    return this.instructorsList;
  }
  set setInstructorsList(value: Instructors[]) {
    this.instructorsList = value;
  }

  get getOpeningDetails(): OpeningDetails[] {
    return this.openingDetails;
  }
  set setOpeningDetails(value: OpeningDetails[]) {
    this.openingDetails = value;
  }

  get getReviewList(): Review[] {
    return this.reviewList;
  }
  set setReviewList(value: Review[]) {
    this.reviewList = value;
  }

  get getStudentsList(): Students[] {
    return this.studentsList;
  }
  set setStudentsList(value: Students[]) {
    this.studentsList = value;
  }

  get getExamRequestList(): ExamRequest[] {
    return this.examRequestList;
  }
  set setExamRequestList(value: ExamRequest[]) {
    this.examRequestList = value;
  }

  get getSchoolJoinRequestList(): SchoolJoinRequest[] {
    return this.schoolJoinRequestList;
  }
  set setSchoolJoinRequestList(value: SchoolJoinRequest[]) {
    this.schoolJoinRequestList = value;
  }
}
