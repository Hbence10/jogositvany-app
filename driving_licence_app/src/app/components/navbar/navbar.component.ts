import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { UsersService } from '../../services/users.service';
import { CookieService } from 'ngx-cookie-service';
import { AlertServiceService } from '../../services/alert-service.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, MatIconModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  private router = inject(Router);
  userService = inject(UsersService);
  private cookieService = inject(CookieService)
  private alertService = inject(AlertServiceService)

  checkRequestList() {
    if (this.userService.loggedUser()?.role.name == "ROLE_instructor") {
      this.router.navigate(["request", "instructor"])
    } else {
      this.router.navigate(["request", "school"])
    }
  }
  navigateToStudents() {
    if (this.userService.loggedUser()?.role.name == "ROLE_instructor") {
      this.router.navigate(["users", "instructorStudents"])
    } else {
      this.router.navigate(["users", "schoolStudent"])
    }
  }

  logout() {
    this.alertService.setAlert("Sikeresen kijelentkeztél!", "success")
    this.userService.logout()
  }


}
