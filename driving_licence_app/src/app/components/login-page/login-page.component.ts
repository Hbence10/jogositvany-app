import { Component, inject, signal } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UsersService } from '../../services/users.service';
import { response } from 'express';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
    private usersService = inject(UsersService);
    private router = inject(Router);
    errorMessage = signal<null | string>(null)

    loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });

    login() {
      this.usersService.login(this.loginForm.controls["email"].value!, this.loginForm.controls["password"].value!).subscribe({
        next: response => {
          console.log(response)
        },
        error: error => {

        },
        complete: () => {
          this.router.navigateByUrl('/home');
        }
      });
    }
}
