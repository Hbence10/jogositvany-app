import { Component, inject, OnInit, signal } from '@angular/core';
import { UsersService } from '../../services/users.service';
import {MatDatepickerModule} from '@angular/material/datepicker';

@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent{
  private userService = inject(UsersService);


}
