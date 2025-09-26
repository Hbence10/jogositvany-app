import { UsersService } from './../../services/users.service';
import { Component, inject, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterModule } from '@angular/router';
import { User } from '../../models/user.model';

function validatePassword(
  control: AbstractControl
): { [key: string]: any } | null {
  const password: string = control.value;

  const specialCharacters: string = '!@#$%^&*()-_=+[]{};:,.?/';
  const numberTexts: string = '1234567890';
  const checkerList: boolean[] = [false, false, false, false];

  for (let i: number = 0; i < password.length; i++) {
    if (specialCharacters.includes(password[i])) {
      checkerList[0] = true;
    } else if (numberTexts.includes(password[i])) {
      checkerList[1] = true;
    } else if (password[i] === password[i].toUpperCase()) {
      checkerList[2] = true;
    } else if (password[i] === password[i].toLowerCase()) {
      checkerList[3] = true;
    }
  }

  if (!checkerList.includes(false)) {
    return null;
  } else {
    return { invalid: false };
  }
}

@Component({
  selector: 'app-registartion-page',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './registartion-page.component.html',
  styleUrl: './registartion-page.component.css',
})
export class RegistartionPageComponent implements OnInit {
  private usersService = inject(UsersService);

  registrationForm!: FormGroup;

  samePasswordValidator = (
    control: AbstractControl
  ): { [key: string]: any } | null => {
    let originalPassword = this.registrationForm.controls['password'].value;
    if (control.value === originalPassword) {
      return null;
    } else {
      return { invalid: false };
    }
  };

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phone: new FormControl('', [Validators.required]),
      birthDate: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      education: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, validatePassword]),
      confirmPassword: new FormControl('', [Validators.required]),
    });
    this.registrationForm.controls['confirmPassword'].addValidators(
      this.samePasswordValidator
    );
  }

  registration() {
    const newUser: User = new User(
      0,
      this.registrationForm.controls['firstName'].value!,
      this.registrationForm.controls['lastName'].value!,
      this.registrationForm.controls['email'].value!,
      this.registrationForm.controls['phone'].value!,
      this.registrationForm.controls['birthDate'].value!,
      this.registrationForm.controls['gender'].value!,
      this.registrationForm.controls['education'].value!,
      this.registrationForm.controls['password'].value!
    );
    this.usersService.registration(newUser).subscribe({
      next: (user) => console.log(user),
    });
  }
}
