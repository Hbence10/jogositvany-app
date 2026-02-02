import { Component, inject } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { Router } from '@angular/router';

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
    this.userService.logout();
  }

  navigate(routerPath: string) {
    this.isOpened = false
    this.router.navigate([routerPath])
  }

  userNavigation() {
    if (this.userService.loggedUser() == null) {
      this.router.navigate(["/login"])
    } else {

    }
  }
}
