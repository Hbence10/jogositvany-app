import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { UsersService } from '../../services/users.service';
import { AlertServiceService } from '../../services/alert-service.service';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  usersService = inject(UsersService);
  private router = inject(Router);
  private alertService = inject(AlertServiceService)
  errorMessage = signal<null | string>(null)
  homePageUser!: HomePageUser
  showError = signal<boolean>(false)
  isRemember: boolean = false

  loginForm = new FormGroup({
    email: new FormControl('testInstructor@gmail.com', [Validators.required, Validators.email]),
    password: new FormControl('test5.Asd', [Validators.required])
  });

  login() {
    console.log("Login gomb megnyomva");
    this.usersService.login(this.loginForm.controls["email"].value!, this.loginForm.controls["password"].value!).subscribe({
      next: response => {
        this.homePageUser = response
      },
      error: error => {
        if (error.status == 500) {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        } else {
          this.showError.set(true)
        }
      },
      complete: () => {
        this.usersService.loggedUser.set(this.homePageUser)
        if (this.isRemember) {
          localStorage.setItem("felutonId", this.usersService.loggedUser()?.id!+"")
        } else {
          sessionStorage.setItem("felutonId", this.usersService.loggedUser()?.id!+"")
        }
        this.router.navigateByUrl('/home');
      }
    });
  }
}
