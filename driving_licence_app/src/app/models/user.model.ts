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
    public intructor?: Instructors,
    public student?: Students,
    public adminSchool?: School,
    public schoolJoinRequestList?: SchoolJoinRequest[],
    public pfpPath: string = '',
    public role?: Role
  ) {
    this.setAsd()
  }

  setAsd(){
    console.log(this.role)
  }

  get getFirstName(): string {
    return this.firstName!;
  }
  set setFirstName(value: string) {
    this.firstName = value;
  }

  get getLastName(): string {
    return this.lastName!;
  }
  set setLastName(value: string) {
    this.lastName = value;
  }

  get getEmail(): string {
    return this.email!;
  }
  set setEmail(value: string) {
    this.email = value!;
  }

  get getPhone(): string {
    return this.phone!;
  }
  set setPhone(value: string) {
    this.phone = value;
  }

  get getBirthDate(): string {
    return this.birthDate!;
  }
  set setBirthDate(value: string) {
    this.birthDate = value;
  }

  get getGender(): 'male' | 'female' | 'other' {
    return this.gender!;
  }
  set setGender(value: 'male' | 'female' | 'other') {
    this.gender = value;
  }

  get getUserEducation(): Education {
    return this.userEducation!;
  }
  set setUserEducation(value: Education) {
    this.userEducation = value;
  }

  get getPassword(): string {
    return this.password!;
  }
  set setPassword(value: string) {
    this.password = value;
  }

  get getInstructor(): Instructors | undefined {
    return this.intructor;
  }
  set setInstructor(value: Instructors | undefined) {
    this.intructor = value;
  }

  get getStudent(): Students | undefined {
    return this.student;
  }
  set setStudent(value: Students | undefined) {
    this.student = value;
  }

  get getAdminSchool(): School | undefined {
    return this.adminSchool;
  }
  set setAdminSchool(value: School | undefined) {
    this.adminSchool = value;
  }

  get getSchoolJoinRequestList(): SchoolJoinRequest[] | undefined {
    return this.schoolJoinRequestList;
  }
  set setSchoolJoinRequestList(value: SchoolJoinRequest[] | undefined) {
    this.schoolJoinRequestList = value;
  }

  get getPfpPath(): string {
    return this.pfpPath;
  }
  set setPfpPath(value: string) {
    this.pfpPath = value;
  }

  get getRole(): Role {
    return this.role!;
  }
  set setRole(value: Role) {
    this.role = value;
  }
}
