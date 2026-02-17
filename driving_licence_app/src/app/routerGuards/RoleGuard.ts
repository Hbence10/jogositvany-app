import { inject, Injectable } from '@angular/core';
import { CanMatch, RedirectCommand, Route, Router, UrlSegment } from '@angular/router';
import { UsersService } from '../services/users.service';
@Injectable({
  providedIn: "root"
})
export class RoleGuard implements CanMatch {
  userService = inject(UsersService)
  router = inject(Router)

  canMatch(route: Route, segments: UrlSegment[]) {
    let data = route.data as { roles: string[] }

    if (data.roles.includes(this.userService.loggedUser()?.role.name!)) {
      return true
    }

    return new RedirectCommand(this.router.parseUrl("/unauthorized"))
  }
}
