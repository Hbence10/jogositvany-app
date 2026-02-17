import { inject, Injectable } from '@angular/core';
import { CanMatch, RedirectCommand, Route, Router, UrlSegment } from '@angular/router';
import { UsersService } from '../services/users.service';
@Injectable({
  providedIn: "root"
})
export class AuthGuard implements CanMatch {
  userService = inject(UsersService)
  router = inject(Router)

  canMatch(route: Route, segments: UrlSegment[]) {
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

    if (this.userService.loggedUser() != null) {
      return true
    }

    return new RedirectCommand(this.router.parseUrl("/unauthorized"))
  }
}
