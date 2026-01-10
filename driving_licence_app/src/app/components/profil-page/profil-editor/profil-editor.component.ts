import { Component, inject, input, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InstructorServiceService } from '../../../services/instructor-service.service';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';
import { SchoolServiceService } from '../../../services/school-service.service';
import { UsersService } from '../../../services/users.service';
import { Education } from '../../../models/education.model';
import { FuelType } from '../../../models/fuel-type.model';
import { VehicleType } from '../../../models/vehicle-type.model';
import { OpeningDetails } from '../../../models/opening-details.model';
import { School } from '../../../models/school.model';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-profil-editor',
  imports: [ReactiveFormsModule],
  templateUrl: './profil-editor.component.html',
  styleUrl: './profil-editor.component.css'
})
export class ProfilEditorComponent implements OnInit {
  userService = inject(UsersService)
  instructorService = inject(InstructorServiceService)
  schoolService = inject(SchoolServiceService)
  otherService = inject(OtherStuffServiceService)

  wantedObject = input.required<User | School>()
  objectType = input.required<"user" | "school">()
  close = output()
  userRole = ""
  educationList: Education[] = []
  fuelTypes: FuelType[] = []
  vehicleTypes: VehicleType[] = []

  profilForm!: FormGroup;
  instructorForm!: FormGroup;
  isInstructor = false;

  openingDetailsForm!: FormGroup
  dayNames: string[] = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]

  ngOnInit(): void {
    if (this.objectType() == "user") {
      const userDetails = (this.wantedObject() as User)

      this.profilForm = new FormGroup({
        firstName: new FormControl(userDetails.firstName, [Validators.required]),
        lastName: new FormControl(userDetails.lastName, [Validators.required]),
        email: new FormControl(userDetails.email, [Validators.required, Validators.required]),
        phone: new FormControl(userDetails.phone, [Validators.required]),
        birthDate: new FormControl(userDetails.birthDate, [Validators.required]),
        gender: new FormControl(userDetails.gender, [Validators.required]),
        education: new FormControl(userDetails.userEducation?.id, [Validators.required])
      })
      this.getAllEducation();

      this.isInstructor = (this.wantedObject() as User).role?.name == "ROLE_instructor"
      if (this.isInstructor) {
        const instructorDetails = (this.wantedObject() as User).instructor

        this.instructorForm = new FormGroup({
          promoText: new FormControl(instructorDetails?.promoText, [Validators.required]),
          vehicleName: new FormControl(instructorDetails?.vehicle?.name, [Validators.required]),
          licensePlate: new FormControl(instructorDetails?.vehicle?.licensePlate, [Validators.required]),
          fuelType: new FormControl(instructorDetails?.vehicle?.fuelType?.id, [Validators.required]),
          vehicleType: new FormControl(instructorDetails?.vehicle?.vehicleType?.id, [Validators.required])
        })

        this.getAllFuelType()
        this.getAllVehicleType()
      }
    } else if (this.objectType() == "school") {
      const schoolDetails = (this.wantedObject() as School)
      this.profilForm = new FormGroup({
        name: new FormControl(schoolDetails.name, [Validators.required]),
        email: new FormControl(schoolDetails.email, [Validators.required, Validators.email]),
        phone: new FormControl(schoolDetails.phone, [Validators.required]),
        country: new FormControl(schoolDetails.country, [Validators.required]),
        town: new FormControl(schoolDetails.town, [Validators.required]),
        address: new FormControl(schoolDetails.address, [Validators.required]),
        promoText: new FormControl(schoolDetails.promoText, [Validators.required])
      })

      this.openingDetailsForm = new FormGroup({});
      for (let i: number = 0; i < 7; i++) {
        this.openingDetailsForm.addControl(this.dayNames[i] + "Start", new FormControl((this.wantedObject() as School).openingDetails[i].getOpeningTime, []))
        this.openingDetailsForm.addControl(this.dayNames[i] + "End", new FormControl((this.wantedObject() as School).openingDetails[i].getCloseTime, []))
      }
    }
  }

  saveChanges() {
    if (this.isInstructor) {
      this.saveInstructorChanges()
    }

    if (this.objectType() == "school") {
      this.saveOpeningChanges()
    }
  }

    saveProfilChanges() {
    if (this.objectType() == "school") {
      this.schoolService.updateSchool(this.wantedObject().id!, this.profilForm.controls["name"].value, this.profilForm.controls["email"].value, this.profilForm.controls["phone"].value, this.profilForm.controls["country"].value, this.profilForm.controls["town"].value, this.profilForm.controls["address"].value, this.profilForm.controls["promoText"].value).subscribe({
        next: response => console.log(response)
      })
    } else {
      this.userService.updateUser(this.wantedObject().id!, this.profilForm.controls["firstName"].value, this.profilForm.controls["lastName"].value, this.profilForm.controls["email"].value, this.profilForm.controls["phone"].value, this.profilForm.controls["birthDate"].value, this.profilForm.controls["gender"].value, this.profilForm.controls["education"].value).subscribe({
        next: response => console.log(response)
      })
    }
  }

  saveInstructorChanges() {
    this.instructorService.updateInstructor((this.wantedObject() as User).instructor?.id!, this.instructorForm.controls["promoText"].value, this.instructorForm.controls["vehicleName"].value, this.instructorForm.controls["licensePlate"].value, this.instructorForm.controls[""].value, this.instructorForm.controls["fuelType"].value, this.instructorForm.controls["vehicleType"].value).subscribe({
      next: response => console.log(response)
    })
  }


  saveOpeningChanges() {
    const cloneList: OpeningDetails[] = []
    for (let i: number = 0; i < this.dayNames.length; i++) {
      const detail: OpeningDetails = (this.wantedObject() as School).openingDetails[i]
      detail.openingTime = new Date("2025-01-01 " + this.openingDetailsForm.controls[`${this.dayNames[i]}Start`].value)
      detail.closeTime = new Date("2025-01-01 " + this.openingDetailsForm.controls[`${this.dayNames[i]}End`].value)
      cloneList.push(detail)
    }

    this.schoolService.updateOpeningDetails(this.wantedObject().id! ,cloneList).subscribe({
      next: response => console.log(response)
    })
  }

  getAllFuelType() {
    this.otherService.getAllFuelType().subscribe({
      next: responseList => {
        console.log(responseList)
        this.fuelTypes = responseList
      }
    })
  }

  getAllVehicleType() {
    this.otherService.getAllVehicleType().subscribe({
      next: responseList => this.vehicleTypes = responseList
    })
  }

  getAllEducation() {
    this.otherService.getAllEducation().subscribe({
      next: responseList => this.educationList = responseList
    })
  }
}

function output() {
  throw new Error('Function not implemented.');
}

