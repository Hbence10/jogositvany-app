import { Component, inject, OnInit, signal } from '@angular/core';
import { UsersService } from '../../services/users.service';
import {MatDatepickerModule} from '@angular/material/datepicker';

@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent implements OnInit{
  private userService = inject(UsersService);
  view = signal<string>("")

  ngOnInit(): void {
    let role = this.userService.loggedUser()!.role.getName;

    if (role === "students"){

    } else if (role === "instructor"){

    } else if (role === "schoolAdmin" || role === "schoolOwner"){

    } else if (role === "admin"){

    }
  }



}
