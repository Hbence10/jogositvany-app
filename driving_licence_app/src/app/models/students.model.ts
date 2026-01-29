import { DrivingLessonRequest } from './driving-lesson-request.model';
import { DrivingLessons } from './driving-lessons.model';
import { DrivingLicenseCategory } from './driving-license.model';
import { ExamRequest } from './exam-request.model';
import { InstructorJoinRequest } from './instructor-join-request.model';
import { Instructors } from './instructors.model';
import { Review } from './review.model';
import { School } from './school.model';
import { User } from './user.model';

export class Students {
  constructor(
    public id: number,
    public studentSchool: School,
    public studentInstructor: Instructors,
    public studentUser: User,
    public reviewList: Review[],
    public requestList: DrivingLessonRequest[],
    public drivingLessons: DrivingLessons[],
    public examRequestList: ExamRequest[],
    public intructorJoinRequestList: InstructorJoinRequest[],
    public selectedCategory: DrivingLicenseCategory
  ) {}

  get getStudentSchool(): School {
    return this.studentSchool;
  }
  set setStudentSchool(value: School) {
    this.studentSchool = value;
  }

  get getStudentInstructor(): Instructors {
    return this.studentInstructor;
  }
  set setStudentInstructor(value: Instructors) {
    this.studentInstructor = value;
  }

  get getStudentUser(): User {
    return this.studentUser;
  }
  set setStudentUser(value: User) {
    this.studentUser = value;
  }

  get getReviewList(): Review[] {
    return this.reviewList;
  }
  set setReviewList(value: Review[]) {
    this.reviewList = value;
  }

  get getRequestList(): DrivingLessonRequest[] {
    return this.requestList;
  }
  set setRequestList(value: DrivingLessonRequest[]) {
    this.requestList = value;
  }

  get getDrivingLessons(): DrivingLessons[] {
    return this.drivingLessons;
  }
  set setDrivingLessons(value: DrivingLessons[]) {
    this.drivingLessons = value;
  }

  get getExamRequestList(): ExamRequest[] {
    return this.examRequestList;
  }
  set setExamRequestList(value: ExamRequest[]) {
    this.examRequestList = value;
  }

  get getIntructorJoinRequestList(): InstructorJoinRequest[] {
    return this.intructorJoinRequestList;
  }
  set setIntructorJoinRequestList(value: InstructorJoinRequest[]) {
    this.intructorJoinRequestList = value;
  }
}
