import { Component, inject, output } from '@angular/core';
import { InstructorServiceService } from '../../../services/instructor-service.service';
import { StudentService } from '../../../services/student.service';
import { UsersService } from '../../../services/users.service';
import { DrivingLessonService } from '../../../services/driving-lesson.service';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { DrivingLessonType } from '../../../models/driving-lesson-type.model';

@Component({
  selector: 'app-request-container',
  imports: [ReactiveFormsModule],
  templateUrl: './request-container.component.html',
  styleUrl: './request-container.component.css'
})
export class RequestContainerComponent {
  studentService = inject(StudentService)
  instructorService = inject(InstructorServiceService)
  userService = inject(UsersService)
  drivingLessonService = inject(DrivingLessonService)

  drivingLessonTypeList: DrivingLessonType[] = []
  requestForm!: FormGroup;
  close = output()

  ngOnInit(): void {
    this.drivingLessonService.getAllDrivingLessonType().subscribe({
      next: response => this.drivingLessonTypeList = response
    })

    this.requestForm = new FormGroup({
      selectedDate: new FormControl("", []),
      startTime: new FormControl("", []),
      endTime: new FormControl("", []),
      selectedType: new FormControl("", []),
      message: new FormControl("", [])
    })
  }

  sendDrivingLessonRequest() {
    const selectedType = this.drivingLessonTypeList.find(dType => dType.id == this.requestForm.controls["selectedType"].value)
  }
}
