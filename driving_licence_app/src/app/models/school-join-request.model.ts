import { DrivingLicenseCategory } from './driving-license.model';
import { School } from './school.model';
import { User } from './user.model';

export class SchoolJoinRequest {
  constructor(
    public id: number,
    public isAccepted: boolean,
    public accpetedAt: Date,
    public sentAt: Date,
    public isDelted: boolean,
    public deletedAt: Date,
    public schoolJoinRequestUser: User,
    public schoolJoinRequestSchool: School,
    public joinRequestCategory: DrivingLicenseCategory
  ) {}
}
