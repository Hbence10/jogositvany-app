import { Component, inject, OnInit, signal } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { StudentService } from '../../services/student.service';
import { ProfilCardComponent } from '../profil-card/profil-card.component';
import { RouterLink } from "@angular/router";
import { ProfileCard } from '../../models/notEntity/profileCard.model';


@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule,  RouterLink, ProfilCardComponent, MatCardModule],
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

    setProfilCardRows(originList: ProfileCard[]): ProfileCard[][]{
    const rows: ProfileCard[][] = []
    for (let i: number = 0; i < originList.length; i+=3){
      const row: ProfileCard[] = []
      for (let j = i; j < i+3; j++) {
        if (originList[j] != undefined) {
          row.push(originList[j])
        }
      }
      rows.push(row)
    }
    return rows;
  }
}
