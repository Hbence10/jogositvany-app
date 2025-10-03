import { Status } from './status.model';
import { Students } from './students.model';
import { User } from './user.model';

export class Request {
  constructor(
    private id: number,
    private createdAt: Date,
    private requestedDate: Date,
    private message: string,
    private isExam: boolean = false,
    private senderUser: User,
    private pickerUser: User,
    private requestStatus: Status,
    private requestedStudent: Students
  ) {}

  get getCreatedAt(): Date {
    return this.createdAt;
  }
  set setCreatedAt(value: Date){
    this.createdAt = value;
  }

  get getRequestedDate(): Date {
    return this.requestedDate;
  }
  set setRequestedDate(value: Date){
    this.requestedDate = value;
  }

  get getMessage(): string {
    return this.message;
  }
  set setMessage(value: string){
    this.message = value;
  }

  get getIsExam(): boolean {
    return this.isExam;
  }
  set setIsExam(value: boolean){
    this.isExam = value;
  }

  get getSenderUser(): User {
    return this.senderUser;
  }
  set setSenderUser(value: User){
    this.senderUser = value;
  }

  get getPickerUser(): User {
    return this.pickerUser;
  }
  set setPickerUser(value: User){
    this.pickerUser = value;
  }

  get getRequestStatus(): Status {
    return this.requestStatus;
  }
  set setRequestStatus(value: Status){
    this.requestStatus = value;
  }

  get getRequestedStudent(): Students {
    return this.requestedStudent;
  }
  set setRequestedStudent(value: Students){
    this.requestedStudent = value;
  }
}
