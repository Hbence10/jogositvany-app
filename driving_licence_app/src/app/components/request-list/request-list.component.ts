import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DrivingLessonRequest } from '../../models/driving-lesson-request.model';
import { ExamRequest } from '../../models/exam-request.model';
import { InstructorJoinRequest } from '../../models/instructor-join-request.model';
import { SchoolJoinRequest } from '../../models/school-join-request.model';
import { InstructorServiceService } from '../../services/instructor-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { RequestCardComponent } from './request-card/request-card.component';
import { DrivingLessonService } from '../../services/driving-lesson.service';

@Component({
  selector: 'app-request-list',
  imports: [RequestCardComponent],
  templateUrl: './request-list.component.html',
  styleUrl: './request-list.component.css',
})
export class RequestListComponent implements OnInit {
  route = inject(ActivatedRoute);
  schoolService = inject(SchoolServiceService);
  instructorService = inject(InstructorServiceService);
  userService = inject(UsersService);
  drivingLessonService = inject(DrivingLessonService);


  requestType!: 'drivingLesson' | 'instructorJoin' | 'schoolJoin' | 'exam';
  ownerType!: 'school' | 'instructor';
  requestList: (
    ExamRequest
    | SchoolJoinRequest
    | DrivingLessonRequest
    | InstructorJoinRequest
  )[] = [];
  requestRowList: (
    ExamRequest
    | SchoolJoinRequest
    | DrivingLessonRequest
    | InstructorJoinRequest
  )[][] = [];

  ngOnInit(): void {
    this.route.params.subscribe({
      next: (parameters) => {
        if (parameters['owner'] == 'school') {
          this.ownerType = 'school';
          this.requestType = 'schoolJoin';
        } else if (parameters['owner'] == 'instructor') {
          this.ownerType = 'instructor';
          this.requestType = 'instructorJoin';
        }
        this.changeRequestList()
      }
    });
  }

  handleRequests(selectedRequest: {
    requestType: 'drivingLesson'
    | 'instructorJoin'
    | 'schoolJoin'
    | 'exam';
    status: 'accept'
    | 'refuse';
    id: number;
  }) {
    if (selectedRequest.requestType == 'drivingLesson') {
      this.instructorService
        .handleDrivingLessonRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({
          error: error =>{},
          complete: () => {}
        });
    } else if (selectedRequest.requestType == 'instructorJoin') {
      this.instructorService
        .handleJoinRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({
          error: error =>{},
          complete: () => {}
        });
    } else if (selectedRequest.requestType == 'schoolJoin') {
      this.schoolService
        .handleJoinRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({
          error: error =>{},
          complete: () => {}
        });
    }
  }

  changeRequestList(){
    if (this.requestType == 'drivingLesson') {
      this.getAllDrivingLessonRequest();
    } else if (this.requestType == 'instructorJoin') {
      this.getAllInstructorJoinRequest();
    } else if (this.requestType == 'schoolJoin') {
      this.getAllSchoolJoinRequest();
    }
  }

  getAllDrivingLessonRequest() {
    this.instructorService
      .getDrivingLessonRequestByInstructor(
        this.userService.loggedUser()?.instructorId!
      )
      .subscribe({
        next: response => console.log(response),
        error: (error) => console.log(error),
      });
  }

  getAllInstructorJoinRequest() {
    this.instructorService
      .getAllJoinRequestByInstructor(
        this.userService.loggedUser()?.instructorId!
      )
      .subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
      });
  }

  getAllSchoolJoinRequest() {
    this.schoolService
      .getAllJoinRequest(this.userService.loggedUser()?.schoolId!)
      .subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
        complete: () => console.log(this.requestList)
      });
  }


  makeRows(): (
    ExamRequest
    | SchoolJoinRequest
    | DrivingLessonRequest
    | InstructorJoinRequest
  ) [][]{
    const rows: (ExamRequest | SchoolJoinRequest | DrivingLessonRequest | InstructorJoinRequest)[][] = []

    for (let i: number = 0; i < this.requestList.length; i += 2) {
      const row: (ExamRequest | SchoolJoinRequest | DrivingLessonRequest | InstructorJoinRequest)[] = []
      for (let j = i; j < i + 2; j++) {
        if (this.requestList[j] != undefined) {
          row.push(this.requestList[j])
        }
      }
      rows.push(row)
    }

    return rows
  }
}
