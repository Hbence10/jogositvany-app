import { UsersService } from './../../services/users.service';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-registartion-page',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './registartion-page.component.html',
  styleUrl: './registartion-page.component.css'
})
export class RegistartionPageComponent {

  private usersService = inject(UsersService);

  registrationForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.required]),
    birthDate: new FormControl('', [Validators.required]),
    gender: new FormControl('', [Validators.required]),
    education: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required])
  });

  registration(){
    const newUser:User = new User(0, this.registrationForm.controls["firstName"].value!,
       this.registrationForm.controls["lastName"].value!,
       this.registrationForm.controls["email"].value!,
       this.registrationForm.controls["phone"].value!,
       this.registrationForm.controls["birthDate"].value!,
       this.registrationForm.controls["gender"].value!,
       this.registrationForm.controls["education"].value!,
       this.registrationForm.controls["password"].value!);
    this.usersService.registration(
      newUser
    ).subscribe({
      next: (user) =>
        console.log(user)

    });
  }
}
