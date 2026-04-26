import { Education } from './education.model';
import { Instructors } from './instructors.model';
import { Role } from './role.model';
import { SchoolJoinRequest } from './school-join-request.model';
import { School } from './school.model';
import { Students } from './students.model';

export class User {
  constructor(
    public id?: number | null,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public birthDate?: string,
    public gender?: 'male' | 'female' | 'other',
    public userEducation?: Education,
    public password?: string,
    public instructor?: Instructors,
    public student?: Students,
    public adminSchool?: School,
    public schoolJoinRequestList?: SchoolJoinRequest[],
    public pfpPath: string = '',
    public role?: Role
  ) {}
}
