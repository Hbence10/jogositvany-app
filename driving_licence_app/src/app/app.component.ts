import { Component, DestroyRef, HostListener, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NavbarPhoneComponent } from './components/navbar-phone/navbar-phone.component';
import { UsersService } from './services/users.service';
import { CookieService } from 'ngx-cookie-service';
import { AlertServiceService } from './services/alert-service.service';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,FooterComponent,NavbarComponent, NavbarPhoneComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  private userService = inject(UsersService)
  private cookieService = inject(CookieService)
  alertService = inject(AlertServiceService)

  ngOnInit(): void {
    let refreshToken: string | null = this.cookieService.get("refreshToken")
    if (refreshToken != null) {
      let id: number | null = null;
      if (sessionStorage.getItem("felutonId") != null) {
        id = +sessionStorage.getItem("felutonId")!
      } else if (localStorage.getItem("felutonId") != null) {
        id = +localStorage.getItem("felutonId")!
      }

      if (id != null) {
        this.userService.getUserAfterReload(id).subscribe({
          next: response => this.userService.loggedUser.set(response)
        })
      }
    }
  }
}
