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
}
