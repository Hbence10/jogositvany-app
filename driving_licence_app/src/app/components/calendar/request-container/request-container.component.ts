import { Component, inject } from '@angular/core';
import { InstructorServiceService } from '../../../services/instructor-service.service';
import { StudentService } from '../../../services/student.service';
import { UsersService } from '../../../services/users.service';
import { DrivingLessonService } from '../../../services/driving-lesson.service';

@Component({
  selector: 'app-request-container',
  imports: [],
  templateUrl: './request-container.component.html',
  styleUrl: './request-container.component.css'
})
export class RequestContainerComponent {
  studentService = inject(StudentService)
  instructorService = inject(InstructorServiceService)
  userService = inject(UsersService)
  drivingLessonService = inject(DrivingLessonService)

}
