import { Component, inject } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { Router } from '@angular/router';
import { AlertServiceService } from '../../services/alert-service.service';

@Component({
  selector: 'app-navbar-phone',
  imports: [],
  templateUrl: './navbar-phone.component.html',
  styleUrl: './navbar-phone.component.css'
})
export class NavbarPhoneComponent {
  isOpened: boolean = false
  userService = inject(UsersService)
  router = inject(Router)
  private alertService = inject(AlertServiceService)

  checkRequestList() {
    if (this.userService.loggedUser()?.role.name == "ROLE_instructor") {
      this.router.navigate(["request", "instructor"])
    } else {
      this.router.navigate(["request", "school"])
    }
    this.isOpened = false;
  }
  navigateToStudents() {
    if (this.userService.loggedUser()?.role.name == "ROLE_instructor") {
      this.router.navigate(["users", "instructorStudents"])
    } else {
      this.router.navigate(["users", "schoolStudent"])
    }
    this.isOpened = false;
  }

  logout() {
    this.alertService.setAlert("Sikeresen kijelentkeztél!", "success")
    this.userService.logout()
    this.isOpened = false;
  }

  navigate(routerPath: string) {
    this.isOpened = false
    this.router.navigate([routerPath])
    this.isOpened = false;
  }

  userNavigation() {
    if (this.userService.loggedUser() == null) {
      this.router.navigate(["/login"])
    } else {
      this.router.navigate([`profil/user/${this.userService.loggedUser()?.id}`])
    }
    this.isOpened = false;
  }
}
