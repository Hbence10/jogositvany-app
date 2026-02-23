import { Component, inject, input, OnChanges, output, SimpleChanges } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { RequestService } from '../../../services/request.service';
import { UsersService } from '../../../services/users.service';
import { AlertServiceService } from '../../../services/alert-service.service';

@Component({
  selector: 'app-request-container',
  imports: [ReactiveFormsModule],
  templateUrl: './request-container.component.html',
  styleUrl: './request-container.component.css'
})
export class RequestContainerComponent implements OnChanges {
  requestService = inject(RequestService)
  userService = inject(UsersService)
  requestForm!: FormGroup;
  close = output()
  reservedHours = input.required<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]>()
  availableHours: string[][] = []
  inputDate = input.required<Date>()
  private alertService = inject(AlertServiceService)
  selectedDate!: Date
  minDate: string = "";
  maxDate: string = "";
  invalidTimeRange: boolean = false

  ngOnChanges(changes: SimpleChanges): void {
    if (this.reservedHours().length != 0) {
      this.getAvailableHours()
    }

    this.selectedDate = this.inputDate()
    this.requestForm = new FormGroup({
      selectedDate: new FormControl(this.selectedDate, [Validators.required]),
      startTime: new FormControl("", [Validators.required]),
      endTime: new FormControl("", [Validators.required]),
      message: new FormControl("", [])
    })

    const now = new Date()
    this.minDate = now.toLocaleDateString().replace(". ", "-").replace(". ", "-").replace(".", "")

    const oneMonthAfter = new Date()
    oneMonthAfter.setMonth(now.getMonth() + 1)
    this.maxDate = oneMonthAfter.toLocaleDateString().replace(". ", "-").replace(". ", "-").replace(".", "")
  }

  sendDrivingLessonRequest() {
    const startTime = new Date(`2026-01-21 ${this.requestForm.controls["startTime"].value}`);
    const endTime = new Date(`2026-01-21 ${this.requestForm.controls["endTime"].value}`);
    if (startTime.getTime() >= endTime.getTime()) {
      this.invalidTimeRange = true;
      this.alertService.setAlert("A kezdési időpontot későbbre adtad meg mint a végzésit!", "error")
      return
    }

    this.requestService.sendDrivingLessonRequest(

      {
        msg: this.requestForm.controls["message"].value,
        date: this.requestForm.controls["selectedDate"].value,
        startTime: this.dateFormatter(new Date(`2026-01-21 ${this.requestForm.controls["startTime"].value}`).toISOString()),
        endTime: this.dateFormatter(new Date(`2026-01-21 ${this.requestForm.controls["endTime"].value}`).toISOString()),
        studentId: this.userService.loggedUser()?.studentId!,
        instructorId: this.userService.loggedUser()?.instructorId!
      }
    ).subscribe({
      next: response => console.log(response),
      error: () => {
        this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
      },
      complete: () => {
        this.alertService.setAlert("Sikeres kérelem küldés!", "success")
        this.close.emit()
      }
    })
  }

  getAvailableHours() {
    let listIndex: number = 0;
    let startDate: Date = new Date("2026-01-11 06:00:00")
    let isBreak = false


    for (let i = 6; i < 21; i++) {
      if (this.convertToValidDate(this.reservedHours()[listIndex].startTime).getHours() == i) {
        const baseDate = this.convertToValidDate(this.reservedHours()[listIndex].startTime)
        for (let j = 0; j < 60; j++) {
          if (baseDate.getMinutes() == j) {
            const endDate = this.convertToValidDate(this.reservedHours()[listIndex].startTime)
            this.availableHours.push([
              `${startDate.getHours() < 10 ? "0" : ""}${startDate.getHours()}:${startDate.getMinutes() < 10 ? "0" : ""}${startDate.getMinutes()}`,
              `${endDate.getHours() < 10 ? "0" : ""}${endDate.getHours()}:${endDate.getMinutes() < 10 ? "0" : ""}${endDate.getMinutes()}`
            ])
            startDate = this.convertToValidDate(this.reservedHours()[listIndex].endTime)
            listIndex += 1
            if (listIndex == this.reservedHours().length) {
              isBreak = true
              break;
            }
          }
        }
        if (isBreak) {
          this.availableHours.push([
            `${startDate.getHours() < 10 ? "0" : ""}${startDate.getHours()}:${startDate.getMinutes() < 10 ? "0" : ""}${startDate.getMinutes()}`,
            `22:00`
          ])
          break;
        }
      }
    }
  }

  convertToValidDate(hourDate: Date) {
    return new Date(`2026-01-01 ${hourDate}`);
  }

  dateFormatter(isoDateString: string): string {
    return isoDateString.replaceAll("T", " ").substring(0, 19);
  }

  setDate(date: string) {
    this.requestForm.controls["selectedDate"].setValue(new Date(date))
  }
}
