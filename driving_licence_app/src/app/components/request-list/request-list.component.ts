import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DrivingLessonRequest } from '../../models/driving-lesson-request.model';
import { ExamRequest } from '../../models/exam-request.model';
import { InstructorJoinRequest } from '../../models/instructor-join-request.model';
import { SchoolJoinRequest } from '../../models/school-join-request.model';
import { InstructorServiceService } from '../../services/instructor-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { RequestCardComponent } from '../request-card/request-card.component';

@Component({
  selector: 'app-request-list',
  imports: [RequestCardComponent],
  templateUrl: './request-list.component.html',
  styleUrl: './request-list.component.css',
})
export class RequestListComponent {
  private route = inject(ActivatedRoute);
  schoolService = inject(SchoolServiceService);
  instructorService = inject(InstructorServiceService);
  userService = inject(UsersService);

  requestType!: 'drivingLesson' | 'instructorJoin' | 'schoolJoin' | 'exam';
  ownerType!: 'school' | 'instructor';
  requestList: (
    | ExamRequest
    | SchoolJoinRequest
    | DrivingLessonRequest
    | InstructorJoinRequest
  )[][] = [];

  ngOnInit(): void {
    this.route.params.subscribe({
      next: (parameters) => {
        if (parameters['type'] == 'school') {
          this.ownerType = 'school';
          this.requestType = 'schoolJoin';
        } else if (parameters['type'] == 'instructor') {
          this.ownerType = 'instructor';
          this.requestType = 'instructorJoin';
        }
      },
      complete: () => {
        this.changeRequestList(this.requestType);
      },
    });
  }

  //Keresek kezelese:
  handleRequests(selectedRequest: {
    requestType: 'drivingLesson' | 'instructorJoin' | 'schoolJoin' | 'exam';
    status: 'accept' | 'refuse';
    id: number;
  }) {
    if (selectedRequest.requestType == 'drivingLesson') {
      this.instructorService
        .handleDrivingLessonRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({});
    } else if (selectedRequest.requestType == 'instructorJoin') {
      this.instructorService
        .handleJoinRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({});
    } else if (selectedRequest.requestType == 'schoolJoin') {
      this.schoolService
        .handleJoinRequest(selectedRequest.id, selectedRequest.status)
        .subscribe({});
    } else if (selectedRequest.requestType == 'exam') {
    }
  }

  changeRequestList(
    newRequestType: 'drivingLesson' | 'instructorJoin' | 'schoolJoin' | 'exam'
  ) {
    this.requestType = newRequestType;
    if (this.requestType == 'drivingLesson') {
      this.getAllDrivingLessonRequest();
    } else if (this.requestType == 'instructorJoin') {
      this.getAllInstructorJoinRequest();
    } else if (this.requestType == 'schoolJoin') {
      this.getAllSchoolJoinRequest();
    } else if (this.requestType == 'exam') {
      this.getAllExamRequest();
    }
  }

  //Keresek lekerese
  getAllDrivingLessonRequest() {
    this.instructorService
      .getDrivingLessonRequestByInstructor(
        this.userService.loggedUser()?.instructorId!
      )
      .subscribe({
        next: (response) => this.setRows(response),
        error: (error) => console.log(error),
      });
  }

  getAllInstructorJoinRequest() {
    this.instructorService
      .getAllJoinRequestByInstructor(
        this.userService.loggedUser()?.instructorId!
      )
      .subscribe({
        next: (response) => this.setRows(response),
        error: (error) => console.log(error),
      });
  }

  getAllSchoolJoinRequest() {
    this.schoolService
      .getAllJoinRequest(this.userService.loggedUser()?.schoolId!)
      .subscribe({
        next: (response) => this.setRows(response),
        error: (error) => console.log(error),
      });
  }

  getAllExamRequest() {
    this.schoolService
      .getAllExamRequest(this.userService.loggedUser()?.schoolId!)
      .subscribe({
        next: (response) => this.setRows(response),
        error: (error) => console.log(error),
      });
  }

  setRows(
    requestList: (
      | ExamRequest
      | SchoolJoinRequest
      | DrivingLessonRequest
      | InstructorJoinRequest
    )[]
  ) {}
}
