import { Component, inject, input, OnInit, output } from '@angular/core';
import { InstructorServiceService } from '../../../services/instructor-service.service';
import { StudentService } from '../../../services/student.service';
import { UsersService } from '../../../services/users.service';
import { DrivingLessonService } from '../../../services/driving-lesson.service';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { DrivingLessonType } from '../../../models/driving-lesson-type.model';
import { DrivingLessonRequest } from '../../../models/driving-lesson-request.model';
import { Instructors } from '../../../models/instructors.model';
import { Students } from '../../../models/students.model';
import { RequestService } from '../../../services/request.service';

@Component({
  selector: 'app-request-container',
  imports: [ReactiveFormsModule],
  templateUrl: './request-container.component.html',
  styleUrl: './request-container.component.css'
})
export class RequestContainerComponent implements OnInit {
  studentService = inject(StudentService)
  instructorService = inject(InstructorServiceService)
  requestService = inject(RequestService)
  drivingLessonService = inject(DrivingLessonService)

  drivingLessonTypeList: DrivingLessonType[] = []
  requestForm!: FormGroup;
  close = output()
  reservedHours = input.required<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]>()
  availableHours: Date[][] = []

  ngOnInit(): void {
    this.drivingLessonService.getAllDrivingLessonType(4).subscribe({
      next: response => this.drivingLessonTypeList = response
    })

    this.requestForm = new FormGroup({
      selectedDate: new FormControl("", [Validators.required]),
      startTime: new FormControl("", [Validators.required]),
      endTime: new FormControl("", [Validators.required]),
      selectedType: new FormControl("", [Validators.required]),
      message: new FormControl("", [])
    })
  }

  sendDrivingLessonRequest() {
    const selectedType = this.drivingLessonTypeList[this.requestForm.controls["selectedType"].value]

    let selectedStudent!: Students;
    let selectedInstructor!: Instructors;

    this.studentService.getStudentById(1).subscribe({
      next: response => selectedStudent = response
    })

    this.instructorService.getInstructorById(1).subscribe({
      next: response => selectedInstructor = response
    })


    const newDrivingLessonRequest = new DrivingLessonRequest(
      null,
      this.requestForm.controls["selectedDate"].value,
      this.requestForm.controls["message"].value,
      this.requestForm.controls["startTime"].value,
      this.requestForm.controls["endTime"].value,
      selectedType!,
      selectedStudent,
      selectedInstructor
    )

    this.requestService.sendDrivingLessonRequest(newDrivingLessonRequest).subscribe({
      next: response => console.log(response),
      complete: () => {
        this.close.emit()
      }
    })
  }

  getAvailableHours() {
    let listIndex: number = 0;
    let startDate: Date = new Date("2026-01-11 06:00:00")

    for (let i = 6; i < 21; i++) {
      if (this.reservedHours()[listIndex].startTime.getHours() == i) {
        const baseDate = this.reservedHours()[listIndex]
        for (let j = 0; j < 60; j++) {
          if (j == baseDate.startTime.getMinutes()){
            console.log(`${i}:${j}`)

          }
        }
      }
    }
  }
}
