import { Component, inject, OnInit, signal } from '@angular/core';
import { UsersService } from '../../services/users.service';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { StudentService } from '../../services/student.service';
import { ProfilCardComponent } from '../profil-card/profil-card.component';
import { RouterLink } from "@angular/router";


@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule,  RouterLink, ProfilCardComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent implements OnInit {
  private userService = inject(UsersService);

  private studentService = inject(StudentService)
  loggedUser!: HomePageUser

  ngOnInit(): void {
    this.loggedUser = this.userService.loggedUser()!

    if (this.loggedUser.role?.name == "ROLE_student"){
      this.studentService.getLessonDetailsForHomePage(1).subscribe({
        next: response => console.log(response)
      })
    }
  }
}
