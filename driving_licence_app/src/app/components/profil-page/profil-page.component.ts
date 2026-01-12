import { Component, inject, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { School } from '../../models/school.model';
import { User } from '../../models/user.model';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';
import { Instructors } from '../../models/instructors.model';
import { error } from 'console';

@Component({
  selector: 'app-profil-page',
  imports: [MatIconModule],
  templateUrl: './profil-page.component.html',
  styleUrl: './profil-page.component.css'
})
export class ProfilPageComponent implements OnInit {
  private route = inject(ActivatedRoute)
  userService = inject(UsersService)
  private schoolService = inject(SchoolServiceService)
  searchedUser: User | null = null
  searchedSchool: School | null = null
  instructorDetails: Instructors | null = null
  type = signal("")
  roleName!: string
  errorMsg: string = ""

  ngOnInit(): void {
    this.route.params.subscribe({

      next: parameters => {
        this.errorMsg = ""
        this.searchedSchool = null
        this.searchedUser = null
        this.type.set(parameters["type"])

        if (this.type() == "user") {
          this.userService.getUserById(parameters["id"]).subscribe({
            next: response => this.searchedUser = response,
            error: error => {
              if (error.status == 404) {
                this.errorMsg = "notFound"
              } else if (error.status == 500) {
                this.errorMsg = "serverError"
              }
            },
            complete: () => {
              if (this.searchedUser?.role?.name == "ROLE_student") {
                this.roleName = "Tanuló"
              } else if (this.searchedUser?.role?.name == "ROLE_instructor") {
                this.roleName = "Oktató",
                this.instructorDetails = this.searchedUser.instructor!
              }
            }
          })
        } else if (this.type() == "school") {
          this.schoolService.getSchoolById(parameters["id"]).subscribe({
            next: response => {
              this.searchedSchool = response
            },
            error: error => {
              if (error.status == 404) {
                this.errorMsg = "notFound"
              } else if (error.status == 500) {
                this.errorMsg = "serverError"
              }
            },
            complete: () => {
              this.roleName = "Iskola"
            }
          })
        }
      }
    })
  }
}
