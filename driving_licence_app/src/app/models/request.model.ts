import { Status } from "./status.model";
import { Students } from "./students.model";
import { User } from "./user.model";

export class Request {
  constructor(
    private id: number,
    private createdAt: Date,
    private requestedDtae: Date,
    private message: string,
    private isExam: boolean = false,
    private senderUser: User,
    private pickerUser: User,
    private requestStatus: Status,
    private requestedStudent: Students,
  ){}
}
