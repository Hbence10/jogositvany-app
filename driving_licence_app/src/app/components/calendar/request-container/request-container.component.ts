import { Component, inject, input, OnInit, output } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { RequestService } from '../../../services/request.service';

@Component({
  selector: 'app-request-container',
  imports: [ReactiveFormsModule],
  templateUrl: './request-container.component.html',
  styleUrl: './request-container.component.css'
})
export class RequestContainerComponent implements OnInit {
  requestService = inject(RequestService)

  requestForm!: FormGroup;
  close = output()
  reservedHours = input.required<{ startTime: Date, endTime: Date, name: string, drivingLessonId: number }[]>()
  availableHours: string[][] = []


  ngOnInit(): void {
    this.getAvailableHours()

    this.requestForm = new FormGroup({
      selectedDate: new FormControl("", [Validators.required]),
      startTime: new FormControl("", [Validators.required]),
      endTime: new FormControl("", [Validators.required]),
      message: new FormControl("", [])
    })
  }

  sendDrivingLessonRequest() {

    this.requestService.sendDrivingLessonRequest(
      {
        msg: this.requestForm.controls["message"].value,
        date: this.requestForm.controls["selectedDate"].value,
        startTime: this.dateFormatter(new Date(`2026-01-21 ${this.requestForm.controls["startTime"].value}`).toISOString()),
        endTime: this.dateFormatter(new Date(`2026-01-21 ${this.requestForm.controls["endTime"].value}`).toISOString()),
        studentId: 10,
        instructorId: 4
      }
    ).subscribe({
      next: response => console.log(response),
      complete: () => {
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
          if (baseDate.getMinutes() == j){
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
}
