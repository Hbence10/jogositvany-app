import { School } from "./school.model";
import { User } from "./user.model";

export class SchoolJoinRequest{
  constructor(
    private id:number,
    private requestedRole:"student" | "instructor",
    private isAccepted:boolean,
    private accpetedat:Date,
    private sentAt:Date,
    private schoolJoinRequestUser:User,
    private schoolJoinRequestShool:School,
  ){}
}
