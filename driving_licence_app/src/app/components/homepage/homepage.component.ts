import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Router, RouterModule } from "@angular/router";
import { HomePageUser } from '../../models/notEntity/homepageUser.model';
import { ProfileCard } from '../../models/notEntity/profileCard.model';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { ProfilCardComponent } from '../profil-card/profil-card.component';
import { SchoolRegistrationComponent } from '../school-registration/school-registration.component';
import { HourPipe } from '../../pipe/HourPipe';
import { StudentService } from '../../services/student.service';
import { BarComponent } from './bar/bar.component';

@Component({
  selector: 'app-homepage',
  imports: [MatDatepickerModule, RouterModule, ProfilCardComponent, MatCardModule, SchoolRegistrationComponent, CommonModule, HourPipe, BarComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss',
})
export class HomepageComponent implements OnInit {
  userService = inject(UsersService);
  private schoolService = inject(SchoolServiceService);
  private router = inject(Router);
  private studentService = inject(StudentService)
  loggedUser: HomePageUser | null = null;
  schoolList: { id: number, name: string }[] = []
  userList: { id: number, name: string, imagePath: string, userId: number }[] = []
  studentList: any[] = []
  showSchoolForm: boolean = false
  lessonDetails!: {paidLesson: number, drivenLesson: number, totalLessonNumber: number}

  ngOnInit(): void {
    this.loggedUser = this.userService.loggedUser();

    if (this.loggedUser?.role?.name == "ROLE_student") {
      this.studentService.getLessonDetailsForHomePage(this.userService.loggedUser()?.studentId!).subscribe({
        next: response => {
          this.lessonDetails = response
        }
      })

    } else if (this.loggedUser?.role?.name == "ROLE_administrator") {
      this.schoolService.getAllSchool(0).subscribe({
        next: response => {
          this.schoolList = response.body
        }
      })

      this.userService.getAllUser().subscribe({
        next: response => {
          this.userList = response.body
        }
      })
    }
  }

  setProfilCardRows(originList: ProfileCard[]): ProfileCard[][] {
    const rows: ProfileCard[][] = []
    for (let i: number = 0; i < originList.length; i += 3) {
      const row: ProfileCard[] = []
      for (let j = i; j < i + 3; j++) {
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
