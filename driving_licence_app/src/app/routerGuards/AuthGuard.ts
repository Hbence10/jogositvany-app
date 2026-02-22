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
    if (this.userService.loggedUser() != null) {
      return true
    }

    return new RedirectCommand(this.router.parseUrl("/unauthorized"))
  }
}
