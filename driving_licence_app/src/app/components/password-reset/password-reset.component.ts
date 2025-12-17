import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink, RouterModule } from "@angular/router";
import { Router } from '@angular/router';
import { UsersService } from '../../services/users.service';


function validatePassword(control: AbstractControl): { [key: string]: any } | null {
  const password: string = control.value

  const specialCharacters: string = "!@#$%^&*()-_=+[]{};:,.?/"
  const numberTexts: string = "1234567890"
  const checkerList: boolean[] = [false, false, false, false]

  for (let i: number = 0; i < password.length; i++) {
    if (specialCharacters.includes(password[i])) {
      checkerList[0] = true
    } else if (numberTexts.includes(password[i])) {
      checkerList[1] = true
    } else if (password[i] === password[i].toUpperCase()) {
      checkerList[2] = true
    } else if (password[i] === password[i].toLowerCase()) {
      checkerList[3] = true
    }
  }

  if (!checkerList.includes(false)) {
    return null
  } else {
    return { invalid: false }
  }
}

@Component({
  selector: 'app-password-reset',
  imports: [RouterModule, CommonModule, ReactiveFormsModule],
  templateUrl: './password-reset.component.html',
  styleUrl: './password-reset.component.css'
})
export class PasswordResetComponent implements OnInit {


  private userService = inject(UsersService)
  private router = inject(Router)
  isShowPassword = signal<boolean>(false)
  isShowPasswordAgain = signal<boolean>(false)

  isCorrectVCode = signal<boolean>(true)
  isSuccessfullReset = signal<boolean>(false)
  form!: FormGroup

  emailErrorMsg = signal<string>("")
  vCodeErrorMsg = signal<string>("")

  samePasswordValidator = (control: AbstractControl): { [key: string]: any } | null => {
    let originalPassword = this.form.controls["password"].value
    if (control.value === originalPassword) {
      return null
    } else {
      return { invalid: false }
    }
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl("", [Validators.required, Validators.email]),
      verificationCode: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required, validatePassword]),
      passwordAgain: new FormControl("", [Validators.required])
    })

    this.form.controls["passwordAgain"].addValidators(this.samePasswordValidator)
  }

  getCode() {
    this.userService.getVerificationCode(this.form.controls["email"].value).subscribe({
      next: response => console.log(response),
      error: error => {
        if (error.status == 404) {
          this.emailErrorMsg.set("Nincs ilyen email címmel létező fiók. Próbáld meg újra!")
        } else if (error.status == 417) {
          this.emailErrorMsg.set("Érvénytelen email címet adtál meg. Próbáld meg újra!")
        }
      }
    })
  }

  checkVCode(vCodeInputValue: string) {
    if (vCodeInputValue.length == 10) {
      this.userService.checkVerificationCode(vCodeInputValue).subscribe({
        next: response => this.isCorrectVCode.set(response),
        error: error => {
          if (error.status == 417) {

          }
        },
        complete: () => {
          if (!this.isCorrectVCode()) {
            this.vCodeErrorMsg.set("Helytelen kódot adtál meg. Próbáld meg újra!")
          }
        }
      })
    }
  }

  sendReset(vCode: string) {
    this.userService.passwordReset(this.form.controls["email"].value, this.form.controls["password"].value, vCode).subscribe({
      next: response => console.log(response),
      complete: () => { this.router.navigate(["/login"]) }
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();
  }

  showPasswordAgain(event: MouseEvent) {
    this.isShowPasswordAgain.update(old => !old)
    event.stopPropagation();
  }

}
