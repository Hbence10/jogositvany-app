import { Component, inject, output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DrivingLessonType } from '../../../models/driving-lesson-type.model';
import { PaymentMethod } from '../../../models/payment-method.model';
import { Status } from '../../../models/status.model';
import { DrivingLessonService } from '../../../services/driving-lesson.service';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';

@Component({
  selector: 'app-driving-lesson-editor',
  imports: [],
  templateUrl: './driving-lesson-editor.component.html',
  styleUrl: './driving-lesson-editor.component.css'
})
export class DrivingLessonEditorComponent {
  otherService = inject(OtherStuffServiceService)
  drivingLessonService = inject(DrivingLessonService)
  close = output()

  paymentMethods: PaymentMethod[] = []
  statusList: Status[] = []
  drivingLessonTypeList: DrivingLessonType[] = []
  drivingLessonForm!: FormGroup;


  ngOnInit(): void {
    this.otherService.getAllPaymentMethod().subscribe({
      next: response => this.paymentMethods = response
    })

    this.otherService.getAllStatus().subscribe({
      next: response => this.statusList = response
    })

    this.drivingLessonService.getAllDrivingLessonType().subscribe({
      next: response => this.drivingLessonTypeList = response
    })

    this.drivingLessonForm = new FormGroup({

    })
  }

  sendSave() {

  }
}
