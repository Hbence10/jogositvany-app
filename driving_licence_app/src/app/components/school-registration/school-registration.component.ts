import { Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SchoolServiceService } from '../../services/school-service.service';
import { AlertServiceService } from '../../services/alert-service.service';
import { OtherStuffServiceService } from '../../services/other-stuff-service.service';

@Component({
  selector: 'app-school-registration',
  imports: [ReactiveFormsModule],
  templateUrl: './school-registration.component.html',
  styleUrl: './school-registration.component.css'
})
export class SchoolRegistrationComponent implements OnInit {
  close = output()
  schoolForm!: FormGroup
  schoolService = inject(SchoolServiceService)
  otherService = inject(OtherStuffServiceService)
  userList: {id: number, email: string, name: string}[] = []
  private alertService = inject(AlertServiceService)
  varmegyek = ["Bács-Kiskun", "Baranya", "Békés", "Borsod-Abaúj-Zemplén", "Csongrád-Csanád", "Fejér", "Győr-Moson-Sopron", "Hajdú-Bihar", "Heves", "Jász-Nagykun-Szolnok", "Komárom-Esztergom", "Nógrád", "Pest", "Somogy", "Szabolcs-Szatmár-Bereg", "Tolna", "Vas", "Veszprém", "Zala"];
  errorMsg: string = ""

  ngOnInit(): void {
    this.schoolForm = new FormGroup({
      name: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.required, Validators.email]),
      phone: new FormControl("", [Validators.required]),
      country: new FormControl("", [Validators.required]),
      town: new FormControl("", [Validators.required]),
      address: new FormControl("", [Validators.required]),
      promoText: new FormControl("", []),
      ownerId: new FormControl("", [Validators.required])
    })

    this.otherService.getAllUser().subscribe({
      next: response => this.userList = response
    })
  }

  createSchool() {
    this.schoolService.createSchool({
      schoolName: this.schoolForm.controls["name"].value,
      email: this.schoolForm.controls["email"].value,
      phoneNumber: this.schoolForm.controls["phone"].value,
      county: this.schoolForm.controls["country"].value,
      town: this.schoolForm.controls["town"].value,
      address: this.schoolForm.controls["address"].value,
      promoText: this.schoolForm.controls["promoText"].value,
      ownerId: this.schoolForm.controls["ownerId"].value
    }).subscribe({
      next: response => {
        this.close.emit()
      }, error: (error) => {
        if (error.error.statusText === "duplicateEmail") {
          this.errorMsg = "duplicateEmail"
          this.alertService.setAlert("Ezzel az e-mail címmel már regisztráltak!", "error")
        } else if (error.error.statusText === "duplicatePhone") {
          this.errorMsg = "duplicatePhone"
          this.alertService.setAlert("Ezzel a telefonszámmal már regisztráltak!", "error")
        } else {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        }
      }, complete: () => {
        this.alertService.setAlert("Sikeresen létrehoztad az iskolát!", "success")
      }
    })

  }
}
