import { Component, input, OnChanges, output, SimpleChanges } from '@angular/core';
import { DrivingLessonRequest } from '../../../models/driving-lesson-request.model';
import { ExamRequest } from '../../../models/exam-request.model';
import { InstructorJoinRequest } from '../../../models/instructor-join-request.model';
import { SchoolJoinRequest } from '../../../models/school-join-request.model';

@Component({
  selector: 'app-request-card',
  imports: [],
  templateUrl: './request-card.component.html',
  styleUrl: './request-card.component.css'
})
export class RequestCardComponent implements OnChanges {
  requestDetail = input.required<ExamRequest | SchoolJoinRequest | DrivingLessonRequest | InstructorJoinRequest>()
  answerRequest = output<{ requestType: "drivingLesson" | "instructorJoin" | "schoolJoin" | "exam", status: "accept" | "refuse", id: number }>()
  requestType = input.required<"drivingLesson" | "instructorJoin" | "schoolJoin" | "exam">()
  lines: string[] = []

  ngOnChanges(changes: SimpleChanges): void {
    if (this.requestType() == "drivingLesson") {
      const drivingRequest = this.requestDetail() as DrivingLessonRequest
      this.lines = [
        `${drivingRequest.dLessonRequestStudent.studentUser.firstName} ${drivingRequest.dLessonRequestStudent.studentUser.lastName} szeretne vezetni`,
        `Az óra ideje: ${drivingRequest.startTime} eddig: ${drivingRequest.endTime}`,
        `Küldte ekkor: ${drivingRequest.sentAt}`
      ]
    } else if (this.requestType() == "instructorJoin") {
      const instructorRequest = this.requestDetail() as InstructorJoinRequest
      this.lines = [
        `${instructorRequest.instructorJoinRequestStudent.studentUser.firstName} ${instructorRequest.instructorJoinRequestStudent.studentUser.lastName} szeretne nálad vezetni`,
        `Küldte ekkor: ${instructorRequest.sentAt}`
      ]
    } else if (this.requestType() == "schoolJoin") {
      const schoolRequest = this.requestDetail() as SchoolJoinRequest
      this.lines = [
        `${schoolRequest.schoolJoinRequestUser.firstName} ${schoolRequest.schoolJoinRequestUser.lastName} szeretne csatlakozni mint ${schoolRequest.requestedRole}`,
        `Küldte ekkor: ${schoolRequest.sentAt}`
      ]
    } else if (this.requestType() == "exam") {
      const examRequest = this.requestDetail() as ExamRequest
      this.lines = [
        `${examRequest.examStudent.studentUser.lastName} ${examRequest.examStudent.studentUser.firstName} szeretne vizsgázni`,
        `A vizsga időpontja: ${examRequest.requestedDate}`,
        `Küldte ekkor: `
      ]
    }
  }

  handleRequest(status: "accept" | "refuse") {
    console.log("handle")
    this.answerRequest.emit({ requestType: this.requestType(), status: status, id: this.requestDetail().id! })
  }
}
