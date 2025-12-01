import { Education } from './education.model';
import { Instructors } from './instructors.model';
import { Role } from './role.model';
import { SchoolJoinRequest } from './school-join-request.model';
import { School } from './school.model';
import { Students } from './students.model';

export class User {
  constructor(
    private id: number | null,
    private firstName: string,
    private lastName: string,
    private email: string,
    private phone: string,
    private birthDate: string,
    private gender: 'male' | 'female' | 'other',
    private userEducation: Education,
    private password: string,
    private intructor?: Instructors,
    private student?: Students,
    private adminSchool?: School,
    private schoolJoinRequestList?: SchoolJoinRequest[],
    private pfpPath: string = '',
    private role: Role = new Role(1, 'user')
  ) {}

  get getFirstName(): string {
    return this.firstName;
  }
  set setFirstName(value: string) {
    this.firstName = value;
  }

  get getLastName(): string {
    return this.lastName;
  }
  set setLastName(value: string) {
    this.lastName = value;
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

  get getBirthDate(): string {
    return this.birthDate;
  }
  set setBirthDate(value: string) {
    this.birthDate = value;
  }

  get getGender(): 'male' | 'female' | 'other' {
    return this.gender;
  }
  set setGender(value: 'male' | 'female' | 'other') {
    this.gender = value;
  }

  get getUserEducation(): Education {
    return this.userEducation;
  }
  set setUserEducation(value: Education) {
    this.userEducation = value;
  }

  get getPassword(): string {
    return this.password;
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
    return this.role;
  }
  set setRole(value: Role) {
    this.role = value;
  }
}
