import { School } from './school.model';
import { User } from './user.model';

export class SchoolJoinRequest {
  constructor(
    private id: number,
    private requestedRole: 'student' | 'instructor',
    private isAccepted: boolean,
    private accpetedat: Date,
    private sentAt: Date,
    private schoolJoinRequestUser: User,
    private schoolJoinRequestShool: School
  ) {}

  get getRequestedRole(): 'student' | 'instructor' {
    return this.requestedRole;
  }
  set setRequestedRole(value: 'student' | 'instructor') {
    this.requestedRole = value;
  }

  get getIsAccepted(): boolean {
    return this.isAccepted;
  }
  set setIsAccepted(value: boolean) {
    this.isAccepted = value;
  }

  get getAcceptedAt(): Date {
    return this.accpetedat;
  }
  set setAcceptedAt(value: Date) {
    this.accpetedat = value;
  }

  get getSentAt(): Date {
    return this.sentAt;
  }
  set setSentAt(value: Date) {
    this.sentAt = value;
  }

  get getSchoolJoinRequestUser(): User {
    return this.schoolJoinRequestUser;
  }
  set setSchoolJoinRequestUser(value: User) {
    this.schoolJoinRequestUser = value;
  }

  get getSchoolJoinRequestSchool(): School {
    return this.schoolJoinRequestShool;
  }
  set setSchoolJoinRequestSchool(value: School) {
    this.schoolJoinRequestShool = value;
  }
}
