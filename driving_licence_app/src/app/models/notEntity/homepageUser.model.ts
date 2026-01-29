import { Role } from "../role.model";
import { School } from "../school.model";
import { Vehicle } from "../vehicle.model";
import { ProfileCard } from "./profileCard.model";

export class HomePageUser {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public pfpPath: string,
    public role: Role,
    public studentId?: number,
    public school?: School,
    public instructor?: {id:number, firstName:string, lastName: string, pfpPath:string},
    public instructorId?: number,
    public schoolId?: number,
    public vehicle?: Vehicle,
    public students?: ProfileCard[],
    public instructors?: ProfileCard[],
    public categoryId?: number
  ){}
}
