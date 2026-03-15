import { Component, input, OnChanges, output, SimpleChanges } from '@angular/core';
import { DrivingLessonRequest } from '../../../models/driving-lesson-request.model';
import { ExamRequest } from '../../../models/exam-request.model';
import { InstructorJoinRequest } from '../../../models/instructor-join-request.model';
import { SchoolJoinRequest } from '../../../models/school-join-request.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-request-card',
  imports: [CommonModule],
  templateUrl: './request-card.component.html',
  styleUrl: './request-card.component.css'
})
export class RequestCardComponent implements OnChanges {
  requestDetail = input.required<SchoolJoinRequest | DrivingLessonRequest | InstructorJoinRequest>()
  answerRequest = output<{ requestType: "drivingLesson" | "instructorJoin" | "schoolJoin" | "exam", status: "accept" | "refuse", id: number }>()
  requestType = input.required<"drivingLesson" | "instructorJoin" | "schoolJoin" | "exam">()
  lines: string[] = []
  pfpPath: string = ""

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.requestDetail())

    if (this.requestType() == "drivingLesson") {
      const drivingRequest = this.requestDetail() as DrivingLessonRequest
      console.log(drivingRequest)
      console.log(drivingRequest.dlessonRequestStudent)
      this.lines = [
        `${drivingRequest?.dlessonRequestStudent?.studentUser.firstName} ${drivingRequest.dlessonRequestStudent.studentUser.lastName}`,
        `Vezetni szeretne ekkor: ${this.convertDate(drivingRequest.date)} ${this.convertHour(drivingRequest.startTime+"")} - ${this.convertHour(drivingRequest.endTime + "")}`,
        `Küldte ekkor: ${this.convertDate(drivingRequest.sentAt)}`
      ]
      this.pfpPath = drivingRequest.dlessonRequestStudent.studentUser.pfpPath
    } else if (this.requestType() == "instructorJoin") {
      const instructorRequest = this.requestDetail() as InstructorJoinRequest
      this.lines = [
        `${instructorRequest.instructorJoinRequestStudent.studentUser.firstName} ${instructorRequest.instructorJoinRequestStudent.studentUser.lastName}`,
        `Nálad szeretne tanulni`,
        `Küldte ekkor: ${this.convertDate(instructorRequest.sentAt)}`
      ]
      this.pfpPath = instructorRequest.instructorJoinRequestStudent.studentUser.pfpPath
    } else if (this.requestType() == "schoolJoin") {
      const schoolRequest = this.requestDetail() as SchoolJoinRequest
      this.lines = [
        `${schoolRequest.schoolJoinRequestUser.firstName} ${schoolRequest.schoolJoinRequestUser.lastName} szeretne csatlakozni mint ${schoolRequest.schoolJoinRequestUser.role!.name == "ROLE_user" ? "diák." : "oktatók."}`,
        `Küldte ekkor: ${this.convertDate(schoolRequest.sentAt)}`
      ]
      this.pfpPath = schoolRequest.schoolJoinRequestUser.pfpPath
    }
  }

  handleRequest(status: "accept" | "refuse") {
    console.log("handle")
    this.answerRequest.emit({ requestType: this.requestType(), status: status, id: this.requestDetail().id! })
  }

  convertHour(hour: string): string {
    return hour.substring(0, 5)
  }

  convertDate(date: Date) {
    console.log(date.toLocaleString())
    console.log(date.toLocaleString("Hu-hu").replace(". ", "-").replace(". ", "-").replace(". ", ""))
    return date.toLocaleString("Hu-hu").replace(". ", "-").replace(". ", "-").replace(". ", "").split("T")[0]
  }
}
