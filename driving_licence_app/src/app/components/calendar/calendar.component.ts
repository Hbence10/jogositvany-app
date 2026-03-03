import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { DrivingLessons } from '../../models/driving-lessons.model';
import { DrivingLessonService } from '../../services/driving-lesson.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { DrivingLessonEditorComponent } from './driving-lesson-editor/driving-lesson-editor.component';
import { RequestContainerComponent } from './request-container/request-container.component';
import { HourPipe } from '../../pipe/HourPipe';
import { HourCard } from '../../models/notEntity/hourCard.model';

@Component({
  selector: 'app-calendar',
  imports: [RequestContainerComponent, DrivingLessonEditorComponent, CommonModule, HourPipe],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent implements OnInit {
  showRequestContainer: boolean = false
  showEditor: boolean = false

  drivingLessonService = inject(DrivingLessonService)
  userService = inject(UsersService)
  schoolService = inject(SchoolServiceService)
  dayNames: string[] = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]
  shortDayNames: string[] = ["H", "K", "SZE", "CS", "P", "SZO", "V"]
  days: { name: string, reservedHours: HourCard[] }[] = []
  selectedDrivingLesson!: DrivingLessons
  selectedReservedHours: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[] = []
  selectedDate: Date = new Date()
  datesOfWeek = signal<Date[]>([])
  numberOfWeek!: number
  dateForRequest!: Date
  selectedDateInPhoneView: Date = new Date()
  reservedHoursOfSingleDate!: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]
  minDate = new Date()

  ngOnInit(): void {
    this.numberOfWeek = this.getWeekOfYear(this.selectedDate)
    this.getReservedHoursOfWeek(this.numberOfWeek)
    this.getReservedHoursOfSingleDate(this.selectedDateInPhoneView)
  }

  getReservedHoursOfSingleDate(wantedDate: Date) {
    const formattedDate: string = wantedDate.toLocaleString('hu-HU').replaceAll(". ", "-").substring(0, 10)
    this.drivingLessonService.getReservedHourByDate(this.userService.loggedUser()?.instructorId!, formattedDate).subscribe({
      next: response => {
        this.reservedHoursOfSingleDate = response
      }
    })
  }

  getReservedHoursOfWeek(numberOfWeek: number) {
    this.days = []
    this.datesOfWeek.set(this.getWeekDates(numberOfWeek, 2026))
    this.drivingLessonService.getReservedHoursBetweenDates(
      this.userService.loggedUser()?.instructorId!,
      this.datesOfWeek()[0].toLocaleString('hu-HU').replaceAll(". ", "-").substring(0, 10),
      this.datesOfWeek()[6].toLocaleString('hu-HU').replaceAll(". ", "-").substring(0, 10),
    ).subscribe({
      next: response => {
        const hourCards: HourCard[] = response
        for (let i: number = 0; i < 7; i++) {
          this.days.push({
            name: this.dayNames[i],
            reservedHours: hourCards.filter((hc) =>
              hc.date.toString().split("T")[0] === this.datesOfWeek()[i].toISOString().split("T")[0]
            )
          })
        }
      }
    })
  }

  changeWeekWithArrow(isForward: boolean) {
    this.numberOfWeek += isForward ? 1 : -1
    this.getReservedHoursOfWeek(this.numberOfWeek)
  }

  changeWeekWithInput(newDate: string) {
    this.numberOfWeek = this.getWeekOfYear(new Date(newDate))
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
    const d = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
    const dayNum = d.getUTCDay() || 7;
    d.setUTCDate(d.getUTCDate() + 4 - dayNum);
    const yearStart = new Date(Date.UTC(d.getUTCFullYear(), 0, 1));
    const weekNumber = Math.ceil((((d.getTime() - yearStart.getTime()) / 86400000) + 1) / 7);
    return weekNumber;
  }

  selectDrivingLesson(id: number) {
    if (this.userService.loggedUser()?.role.name == "ROLE_instructor") {
      this.drivingLessonService.getDrivingLessonById(id).subscribe({
        next: response => this.selectedDrivingLesson = response,
        complete: () => this.showEditor = true
      })
    }
  }

  sendRequest(dateIndex: number, reservedHours: { startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]) {
    this.dateForRequest = this.datesOfWeek()[dateIndex]
    this.selectedReservedHours = reservedHours
    this.showRequestContainer = true
  }

  sendRequestInPhone() {
    this.dateForRequest = this.selectedDateInPhoneView
    this.selectedReservedHours = this.reservedHoursOfSingleDate
    this.showRequestContainer = true
  }

  changeDateInPhone(isTommorrow: boolean) {
    const weeksDate = this.datesOfWeek().map((date) => date.toISOString().split("T")[0])
    let nextDate: Date = new Date(this.selectedDateInPhoneView);
    nextDate.setDate(nextDate.getDate() + (isTommorrow ? +1 : -1))
    console.log(nextDate)
    if (!weeksDate.includes(nextDate.toDateString())) {
      this.datesOfWeek.set(this.getWeekDates(this.getWeekOfYear(nextDate), 2026))
      console.log("")
      const nextWeekNumber: number = this.getWeekOfYear(nextDate)
      console.log(nextWeekNumber)
      console.log(this.datesOfWeek())
    }

    this.getReservedHoursOfSingleDate(nextDate)
    this.selectedDateInPhoneView = nextDate
  }
}
