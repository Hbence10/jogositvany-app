import { Component, inject, OnInit } from '@angular/core';
import { DrivingLessonEditorComponent } from './driving-lesson-editor/driving-lesson-editor.component';
import { RequestContainerComponent } from './request-container/request-container.component';
import { DrivingLessonService } from '../../services/driving-lesson.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { DrivingLessons } from '../../models/driving-lessons.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-calendar',
  imports: [RequestContainerComponent, DrivingLessonEditorComponent, CommonModule],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent implements OnInit{
  showRequestContainer: boolean = false
  showEditor: boolean = false

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

  selectedDate: Date = new Date()
  datesOfWeek: Date[] = []
  numberOfWeek!: number
  dateForRequest!: Date

  ngOnInit(): void {
    this.numberOfWeek = this.getWeekOfYear(this.selectedDate) - 1
    this.getReservedHoursOfWeek(this.numberOfWeek)
  }
  getReservedHoursOfWeek(numberOfWeek: number) {
    this.days = []
    this.datesOfWeek = this.getWeekDates(numberOfWeek, 2026)
    console.log(this.datesOfWeek)

    for (let i: number = 0; i < this.datesOfWeek.length; i++) {
      const formattedDate: string = this.datesOfWeek[i].toLocaleString('hu-HU').replaceAll(". ", "-").substring(0, 10)
      console.log(formattedDate)
      this.drivingLessonService.getReservedHourByDate(this.userService.loggedUser()?.instructorId!, formattedDate).subscribe({
        next: response => {
          console.log(response)
          this.days.push({ name: this.dayNames[i], reservedHours: response })
        }
      })
    }
  }
  changeWeekWithArrow(isForward: boolean) {
    this.numberOfWeek += isForward ? 1 : -1
    this.getReservedHoursOfWeek(this.numberOfWeek)
  }

  changeWeekWithInput(newDate: Date) {
    this.numberOfWeek = this.getWeekOfYear(newDate) - 1
    this.getReservedHoursOfWeek(this.numberOfWeek)
  }


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

  selectDrivingLesson(id: number) {
    if(this.userService.loggedUser()?.role.name == "ROLE_instructor"){
      this.drivingLessonService.getDrivingLessonById(id).subscribe({
        next: response => this.selectedDrivingLesson = response,
        complete: () => this.showEditor = true
      })
    }

  }

  sendRequest(dateIndex: number, reservedHours: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]) {
    this.dateForRequest = this.datesOfWeek[dateIndex]
    this.selectedReservedHours = reservedHours
    this.showRequestContainer = true
  }

}
