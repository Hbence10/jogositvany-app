import { Role } from "./role.model";

export class User {
  constructor(
    public id: number | null,
    public firstName: string,
    public lastName: string,
    public email: string,
    public phone: string,
    public birthDate: string,
    public gender: string,
    public educationQualification: string,
    public password: string,
    public role : Role = new Role(1, "user")
  ) {}

  get getFirstName(): string {
    return this.firstName;
  }
  set setFirstName(value: string){
    this.firstName = value;
  }

  get getLastName(): string {
    return this.lastName;
  }
  set setLastName(value: string){
    this.lastName = value;
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

  get getBirthDate(): string {
    return this.birthDate;
  }
  set setBirthDate(value: string) {
    this.birthDate = value;
  }

  get getGender(): string {
    return this.gender;
  }
  set setGender(value: string){
    this.gender = value;
  }

  get getEducationQualification(): string {
    return this.educationQualification;
  }
  set setEducationQualification(value: string) {
    this.educationQualification = value;
  }

  get getPassword(): string {
    return this.password;
  }
  set setPassword(value: string) {
    this.password = value;
  }

  get getRole(): Role {
    return this.role;
  }
  set setRole(value: Role) {
    this.role = value;
  }
}
