import { Component, inject } from '@angular/core';
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

    loginForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });

    login() {
      this.usersService.login(this.loginForm.controls["email"].value!, this.loginForm.controls["password"].value!).subscribe({
        next: response => {
          this.usersService.loggedUser.set(response);
        },
        complete: () => {
          this.router.navigateByUrl('/home');
        }
      });
    }
}
