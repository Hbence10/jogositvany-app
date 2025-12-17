import { Component, inject, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { School } from '../../models/school.model';
import { User } from '../../models/user.model';
import { SchoolServiceService } from '../../services/school-service.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-profil-page',
  imports: [MatIconModule],
  templateUrl: './profil-page.component.html',
  styleUrl: './profil-page.component.css'
})
export class ProfilPageComponent implements OnInit {
 private route = inject(ActivatedRoute)
  private userService = inject(UsersService)
  private schoolService = inject(SchoolServiceService)
  searchedUser: User | null = null
  searchedSchool: School | null = null
  type = signal("")
  roleName!: string
  isError: boolean = false

  ngOnInit(): void {
    this.route.params.subscribe({

      next: parameters => {
        this.searchedSchool = null
        this.searchedUser = null
        this.type.set(parameters["type"])

        if (parameters["type"] == "user") {
          this.userService.getUserById(parameters["id"]).subscribe({
            next: response => this.searchedUser = response,
            error: () => this.isError = true,
            complete: () => {
              if (this.searchedUser?.role?.name == "ROLE_student") {
                this.roleName = "Tanuló"
              } else if (this.searchedUser?.role?.name == "ROLE_instructor") {
                this.roleName = "Oktató"
              }
            }
          })
        } else if (parameters["type"] == "school") {
          this.schoolService.getSchoolById(parameters["id"]).subscribe({
            next: response => this.searchedSchool = response,
            error: () => this.isError = true,
            complete: () => {
              this.roleName = "Iskola"
            }
          })
        }
      }
    })
  }
}
