import { Component, inject, input, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PaymentMethod } from '../../../models/payment-method.model';
import { Status } from '../../../models/status.model';
import { DrivingLessonService } from '../../../services/driving-lesson.service';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';
import { DrivingLessons } from '../../../models/driving-lessons.model';
import { AlertServiceService } from '../../../services/alert-service.service';

@Component({
  selector: 'app-driving-lesson-editor',
  imports: [ReactiveFormsModule],
  templateUrl: './driving-lesson-editor.component.html',
  styleUrl: './driving-lesson-editor.component.css'
})
export class DrivingLessonEditorComponent implements OnInit {
  otherService = inject(OtherStuffServiceService)
  drivingLessonService = inject(DrivingLessonService)
  close = output()

  paymentMethods: PaymentMethod[] = []
  statusList: Status[] = []
  drivingLessonForm!: FormGroup;
  drivingLesson = input.required<DrivingLessons>()
  isPaid: boolean = false
  private alertService = inject(AlertServiceService)

  ngOnInit(): void {
    this.otherService.getAllPaymentMethod().subscribe({
      next: response => this.paymentMethods = response
    })

    this.otherService.getAllStatus().subscribe({
      next: response => this.statusList = response
    })
    console.log(this.isPaid)
    this.isPaid = this.drivingLesson().isPaid

    this.drivingLessonForm = new FormGroup({
      startKm: new FormControl(this.drivingLesson().startKm, []),
      endKm: new FormControl(this.drivingLesson().endKm, []),
      location: new FormControl(this.drivingLesson().location, []),
      pickUpPlace: new FormControl(this.drivingLesson().pickUpPlace, []),
      dropOffPlace: new FormControl(this.drivingLesson().dropOffPlace, []),
      lessonHourNumber: new FormControl(this.drivingLesson().lessonHourNumber, []),
      paymentMethod: new FormControl( this.drivingLesson().paymentMethod.id, []),
      lessonStatus: new FormControl(this.drivingLesson().drivingLessonStatus.id, [])
    })
  }

  sendSave() {
    const body = {
      startKm: +this.drivingLessonForm.controls["startKm"].value,
      endKm: +this.drivingLessonForm.controls["endKm"].value,
      location: this.drivingLessonForm.controls["location"].value,
      pickUpPlace: this.drivingLessonForm.controls["pickUpPlace"].value,
      dropOffPlace: this.drivingLessonForm.controls["dropOffPlace"].value,
      lessonHourNumber: +this.drivingLessonForm.controls["lessonHourNumber"].value,
      isPaid: this.isPaid,
      statusId: this.drivingLessonForm.controls["lessonStatus"].value,
      paymentMethodId: this.drivingLessonForm.controls["paymentMethod"].value
    }

    this.drivingLessonService.updateDrivingLesson(this.drivingLesson().id, body).subscribe({
      next: response => console.log(response),
      error: () => {
        this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
      },
      complete: () => {
        this.alertService.setAlert("Sikeres frissités!", "success")
        this.close.emit()
      }
    })
  }

  cancelDrivingLesson() {
    this.drivingLessonService.cancelDrivingLesson(this.drivingLesson().id).subscribe({
      error: () => {
        this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
      },
      complete: () => {
        this.alertService.setAlert("Sikeresen lemondtad az órád!", "success")
        this.close.emit()
      }
    })
  }
}
