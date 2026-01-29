import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from 'express';
import { Subscription } from 'rxjs';
import { InstructorServiceService } from '../../services/instructor-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { StudentService } from '../../services/student.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{
  private schoolService = inject(SchoolServiceService)
  private instructorService = inject(InstructorServiceService)
  private userService = inject(UsersService)
  private studentService = inject(StudentService)
  private destroyRef = inject(DestroyRef)

  private route = inject(ActivatedRoute)
  private router = inject(Router)

  cardList: { id: number, name: string, imagePath: string, userId: number }[] = []
  deleteText: string = ""
  userType = ""

  ngOnInit(): void {
    let sub: Subscription

    this.route.params.subscribe({
      next: parameter => {
        this.userType = parameter["userType"]
        if (this.userType == "schoolStudent") {
          this.deleteText = "Diák kirugása"
          sub = this.schoolService.getMembersOfSchool(this.userService.loggedUser()?.schoolId!, "students").subscribe({
            next: response => this.cardList = response
          })
        } else if (this.userType == "instructors") {
          this.deleteText = "Oktató kirugása"
          sub = this.schoolService.getMembersOfSchool(this.userService.loggedUser()?.schoolId!, "instructors").subscribe({
            next: response => this.cardList = response
          })
        } else if (this.userType == "instructorStudents") {
          this.deleteText = "Diák kirugása"
          sub = this.instructorService.getStudents(this.userService.loggedUser()?.instructorId!).subscribe({
            next: response => this.cardList = response
          })
        } else if (this.userType === "users") {
          this.deleteText = "Felhasználó törloése"
          sub = this.userService.getAllUser().subscribe({
            next: response => this.cardList = response
          })
        } else if (this.userType === "school") {
          this.deleteText = "Iskola törloése"
          sub = this.schoolService.getAllSchool().subscribe({
            next: response => console.log(response)
          })
        }
      }
    })

    this.destroyRef.onDestroy(() => {
      sub.unsubscribe()
    })
  }

  navigateToUserPage(selectedCard: { id: number, name: string, imagePath: string, userId: number }) {
    this.router.navigate(["profil", "user", selectedCard.userId])
  }

  getCardRow(): { id: number, name: string, imagePath: string, userId: number }[][] {
    const rows: { id: number, name: string, imagePath: string, userId: number }[][] = []
    for (let i = 0; i < this.cardList.length; i += 5) {
      const row: { id: number, name: string, imagePath: string, userId: number }[] = []
      for (let j = i; j < i + 5; j++) {
        if (this.cardList[j] != undefined) {
          row.push(this.cardList[j])
        }
      }
      rows.push(row)
    }

    return rows
  }

  deleteMember(selectedCard: { id: number, name: string, imagePath: string, userId: number }) {
    let index = this.cardList.indexOf(this.cardList.find(card => card.id === selectedCard.id)!)

    if (this.userType == "schoolStudent") {
      this.studentService.deleteStudent(selectedCard.id).subscribe({
        next: response => console.log(response),
        complete: () => {
          this.cardList.splice(index, 1)
        }
      })
    } else if (this.userType == "instructors") {
      this.schoolService.kickOutInstructor(selectedCard.id).subscribe({
        next: response => console.log(response),
        complete: () => {
          this.cardList.splice(index, 1)
        }
      })
    } else if (this.userType == "instructorStudents") {
      this.instructorService.kickoutStudent(selectedCard.id).subscribe({
        next: response => console.log(response),
        complete: () => {
          this.cardList.splice(index, 1)
        }
      })
    } else if (this.userType == "users") {
      this.userService.deleteUser(selectedCard.id).subscribe({
        next: response => console.log(response),
        complete: () => {
          this.cardList.splice(index, 1)
        }
      })
    }
  }
}
