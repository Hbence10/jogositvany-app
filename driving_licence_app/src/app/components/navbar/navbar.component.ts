import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, MatIconModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  private router = inject(Router);
  userService = inject(UsersService);
  loggedUser!: HomePageUser;

  ngOnInit(): void {
    this.loggedUser = this.userService.loggedUser()!;
  }

  checkRequestList() {
    if (this.loggedUser.role.name == 'ROLE_instructor') {
      this.router.navigate([
        'searchPage',
        'instructor',
        this.userService.loggedUser()?.instructorId,
      ]);
    } else {
      this.router.navigate([
        'searchPage',
        'school',
        this.userService.loggedUser()?.schoolId,
      ]);
    }
  }
}
