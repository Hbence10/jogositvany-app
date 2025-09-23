import { Component, inject, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-homepage',
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent implements OnInit{
  private userService = inject(UsersService);

  ngOnInit(): void {
    // let role = this.userService.loggedUser().role;
  }
}
