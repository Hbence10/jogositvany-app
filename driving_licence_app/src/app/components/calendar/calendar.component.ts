import { Component, inject } from '@angular/core';
import { DrivingLessonEditorComponent } from './driving-lesson-editor/driving-lesson-editor.component';
import { RequestContainerComponent } from './request-container/request-container.component';
import { DrivingLessonService } from '../../services/driving-lesson.service';

@Component({
  selector: 'app-calendar',
  imports: [RequestContainerComponent, DrivingLessonEditorComponent],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent {
  showRequestContainer: boolean = false
  showEditor: boolean = true

  drivingLessonService = inject(DrivingLessonService)

  days: {name: string, lessons: number[]}[] = [
    { name: 'Hétfő', lessons: [1] },
    { name: 'Kedd', lessons: [1, 2, 3] },
    { name: 'Szerda', lessons: [1, 2] },
    { name: 'Csütörtök', lessons: [1, 2] },
    { name: 'Péntek', lessons: [1, 2] },
    { name: 'Szombat', lessons: [1, 2] },
    { name: 'Vasárnap', lessons: [1, 2] }
  ]
}
