import { Component, inject } from '@angular/core';
import { DrivingLessonEditorComponent } from './driving-lesson-editor/driving-lesson-editor.component';
import { RequestContainerComponent } from './request-container/request-container.component';
import { DrivingLessonService } from '../../services/driving-lesson.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { DrivingLessons } from '../../models/driving-lessons.model';

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
  userService = inject(UsersService)
  schoolService = inject(SchoolServiceService)

  dayNames: string[] = [
    "Hétfő",
    "Kedd",
    "Szerda",
    "Csütörtök",
    "Péntek",
    "Szombat",
    "Vasárnap"
  ]
  days: { name: string, reservedHours: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[] }[] = []
  selectedDrivingLesson!: DrivingLessons
  selectedReservedHours: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[] = []

  getWeekDates(week: number, year: number): Date[] {
    const start = new Date(year, 0, 1 + (week - 1) * 7);
    let day = start.getDay();

    day = day === 0 ? 6 : day - 1;

    const weekStart = new Date(start);
    weekStart.setDate(start.getDate() - day);

    const days: Date[] = [];

    for (let i = 0; i < 7; i++) {
      const d = new Date(weekStart);
      d.setDate(weekStart.getDate() + i);
      days.push(d);
    }

    return days;
  }

  getWeekOfYear(date: Date): number {
    const start = new Date(date.getFullYear(), 0, 1);
    const diff = date.getTime() - start.getTime();

    const dayCount = diff / 86400000;
    return Math.ceil((dayCount + start.getDay() + 1) / 7);
  }




}
