import { Component, inject, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Router } from "@angular/router";
import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { ProfileCard } from '../../models/notEntity/profileCard.model';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { ProfilCardComponent } from '../profil-card/profil-card.component';
import { SchoolRegistrationComponent } from '../school-registration/school-registration.component';


@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule, ProfilCardComponent, MatCardModule, SchoolRegistrationComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css',

})
export class HomepageComponent implements OnInit {
  private userService = inject(UsersService);
  private schoolService = inject(SchoolServiceService);
  private router = inject(Router);
  loggedUser!: HomePageUser;
  schoolList: {id: number, name: string}[] = []
  userList: {id:number, name: string, imagePath: string, userId: number}[] = []
  studentList: any[] = []
  showSchoolForm: boolean = false

  ngOnInit(): void {
    this.loggedUser = this.userService.loggedUser()!;

    if (this.loggedUser.role?.name == "ROLE_student"){

    } else if (this.loggedUser.role?.name == "ROLE_administrator") {
      this.schoolService.getAllSchool().subscribe({
        next: response => {
          this.schoolList = response
          console.log(this.schoolList)
        }
      })

      this.userService.getAllUser().subscribe({
        next: response => this.userList = response
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

  navigateToProfilPage(type: "user" | "school", id: number) {
    this.router.navigate(["profil", type, id])
  }
}
