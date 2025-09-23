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
}
