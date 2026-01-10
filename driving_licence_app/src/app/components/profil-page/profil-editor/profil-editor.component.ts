import { Component, inject, input } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { InstructorServiceService } from '../../../services/instructor-service.service';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';
import { SchoolServiceService } from '../../../services/school-service.service';
import { UsersService } from '../../../services/users.service';
import { Education } from '../../../models/education.model';

@Component({
  selector: 'app-profil-editor',
  imports: [ReactiveFormsModule],
  templateUrl: './profil-editor.component.html',
  styleUrl: './profil-editor.component.css'
})
export class ProfilEditorComponent {
  userService = inject(UsersService)
  instructorService = inject(InstructorServiceService)
  schoolService = inject(SchoolServiceService)
  otherService = inject(OtherStuffServiceService)

  objectType = input.required<"user" | "school">()
  educationList: Education[] = []
}

