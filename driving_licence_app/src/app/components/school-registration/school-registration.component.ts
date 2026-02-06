import { Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-school-registration',
  imports: [ReactiveFormsModule],
  templateUrl: './school-registration.component.html',
  styleUrl: './school-registration.component.css'
})
export class SchoolRegistrationComponent implements OnInit{
  close = output()
  schoolForm!: FormGroup
  schoolService = inject(SchoolServiceService)
  userService = inject(UsersService)
  varmegyek = ["Bács-Kiskun","Baranya","Békés","Borsod-Abaúj-Zemplén","Csongrád-Csanád","Fejér","Győr-Moson-Sopron","Hajdú-Bihar","Heves","Jász-Nagykun-Szolnok","Komárom-Esztergom","Nógrád","Pest","Somogy","Szabolcs-Szatmár-Bereg","Tolna","Vas","Veszprém","Zala"];

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
  }
}
