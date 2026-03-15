import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InstructorServiceService } from '../../services/instructor-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { StudentService } from '../../services/student.service';
import { UsersService } from '../../services/users.service';
import { AlertServiceService } from '../../services/alert-service.service';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {
  private schoolService = inject(SchoolServiceService)
  private instructorService = inject(InstructorServiceService)
  private userService = inject(UsersService)
  private studentService = inject(StudentService)
  private destroyRef = inject(DestroyRef)

  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private alertService = inject(AlertServiceService)

  cardList: { id: number, name: string, imagePath: string, userId: number }[] = []
  deleteText: string = ""
  userType = ""
  title: string = ""
  availablePages: number[] = []
  actualPage: number = 0;
  showDeleteConfirm: boolean = false
  deleteConfirmText: string = ""
  selectedCard!: { id: number, name: string, imagePath: string, userId: number }

  ngOnInit(): void {
    let sub: Subscription

    this.route.params.subscribe({
      next: parameter => {
        this.userType = parameter["userType"]
        this.changePage(0)
      }
    })
  }

  handleDelete(selectedCard: { id: number, name: string, imagePath: string, userId: number }) {
    this.selectedCard = selectedCard
    this.showDeleteConfirm = true
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

  deleteMember() {
    let index = this.cardList.indexOf(this.cardList.find(card => card.id === this.selectedCard.id)!)

    if (this.userType == "schoolStudent") {
      this.studentService.deleteStudent(this.selectedCard.id).subscribe({
        next: response => console.log(response),
        error: () => {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        },
        complete: () => {
          this.alertService.setAlert("Sikeresen kirugásra került!", "success")
          this.cardList.splice(index, 1)
          this.changePage(this.actualPage)
        }
      })
    } else if (this.userType == "instructors") {
      this.schoolService.kickOutInstructor(this.selectedCard.id).subscribe({
        next: response => console.log(response),
        error: () => {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        },
        complete: () => {
          this.alertService.setAlert("Sikeresen kirugásra került!", "success")
          this.cardList.splice(index, 1)
          this.changePage(this.actualPage)
        }
      })
    } else if (this.userType == "instructorStudents") {
      this.instructorService.kickoutStudent(this.selectedCard.id).subscribe({
        next: response => console.log(response),
        error: () => {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        },
        complete: () => {
          this.alertService.setAlert("Sikeresen kirugásra került!", "success")
          this.cardList.splice(index, 1)
          this.changePage(this.actualPage)
        }
      })
    } else if (this.userType == "users") {
      this.userService.deleteUser(this.selectedCard.id).subscribe({
        next: response => console.log(response),
        error: () => {
          this.alertService.setAlert("Hiba történt. Próbáld meg újra később!", "error")
        },
        complete: () => {
          this.alertService.setAlert("Sikeresen kirugásra került!", "success")
          this.cardList.splice(index, 1)
          this.changePage(this.actualPage)
        }
      })
    }
    this.showDeleteConfirm = false
  }

  changePage(pageNumber: number) {
    if (this.userType == "schoolStudent") {
      this.deleteText = "Diák kirugása"
      this.deleteConfirmText = "Biztosan kirugod a diákot?"
      this.schoolService.getMembersOfSchool(this.userService.loggedUser()?.schoolId!, "students", pageNumber).subscribe({
        next: response => {
          this.availablePages = Array(+response.headers.get("pagenumber")!).fill(1)
          this.cardList = response.body
        }
      })
      this.title = "Diákjaink:"
    } else if (this.userType == "instructors") {
      this.deleteText = "Oktató kirugása"
      this.deleteConfirmText = "Biztosan kirugod az oktatót?"
      this.schoolService.getMembersOfSchool(this.userService.loggedUser()?.schoolId!, "instructors", pageNumber).subscribe({
        next: response => {
          this.availablePages = Array(+response.headers.get("pagenumber")!).fill(1)
          this.cardList = response.body
        }
      })
      this.title = "Oktatóink:"
    } else if (this.userType == "instructorStudents") {
      this.deleteText = "Diák kirugása"
      this.deleteConfirmText = "Biztosan kirugod a diákot?"
      this.instructorService.getStudents(this.userService.loggedUser()?.instructorId!, pageNumber).subscribe({
        next: response => {
          this.availablePages = Array(+response.headers.get("pagenumber")!).fill(1)
          this.cardList = response.body
        }
      })
      this.title = "Diákjaim"
    } else if (this.userType === "users") {
      this.deleteText = "Felhasználó törlése"
      this.deleteConfirmText = "Biztosan törlöd a felhasználót?"
      this.userService.getAllUser(pageNumber).subscribe({
        next: response => {
          this.availablePages = Array(+response.headers.get("pagenumber")!).fill(1)
          this.cardList = response.body
        }
      })
      this.title = "Felhasználók:"
    } else if (this.userType === "school") {
      this.deleteText = "Iskola törloése"
      this.deleteConfirmText = "Biztosan törlöd az iskolát?"
      this.schoolService.getAllSchool(0).subscribe({
        next: response => {
          this.availablePages = Array(+response.headers.get("pagenumber")!).fill(1)
          this.cardList = response.body
        }
      })
      this.title = "Iskolák:"
    }
  }

  changePageWithArrow(newPage: 1 | -1) {
    if (newPage + this.actualPage == this.availablePages.length || newPage + this.actualPage == -1) {

    } else {
      this.actualPage += newPage
      this.changePage(this.actualPage-1)
    }
  }
}
