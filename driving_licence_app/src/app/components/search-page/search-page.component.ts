import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FuelType } from '../../models/fuel-type.model';
import { Instructors } from '../../models/instructors.model';
import { School } from '../../models/school.model';
import { OtherStuffServiceService } from '../../services/other-stuff-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { InstructorServiceService } from '../../services/instructor-service.service';
import { UsersService } from '../../services/users.service';
import { RequestService } from '../../services/request.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './search-page.component.html',
  styleUrl: './search-page.component.css'
})
export class SearchPageComponent implements OnInit {

  private otherStuffService = inject(OtherStuffServiceService)
  private schoolService = inject(SchoolServiceService)
  private instructorService = inject(InstructorServiceService)
  private route = inject(ActivatedRoute)
  private router = inject(Router)
  userService = inject(UsersService)
  private requestService = inject(RequestService)

  fuelTypeList: FuelType[] = []
  townList: string[] = []
  selectedFuelType!: FuelType
  selectedType: string = ""
  selectedTown: string = "Budapest"
  selectedSchool: School | null = null
  filteredTownList: string[] = []
  schoolList: { id: number, name: string }[] = []
  filteredSchoolList: { id: number, name: string }[] = []
  selectedFuelTypeId: number = 1;
  selectedInstructor: Instructors | null = null
  instructorList: { id: number, name: string }[] = []
  filteredInstructorList: { id: number, name: string }[] = []
  selectedCategoryId: number | null = null

  ngOnInit(): void {
    this.selectedSchool = null
    this.selectedInstructor = null
    this.selectedFuelTypeId = 1

    this.route.params.subscribe({
      next: param => {
        this.selectedType = param["type"]
        if (this.selectedType == "instructor") {
          this.otherStuffService.getAllFuelType().subscribe({
            next: responseList => this.fuelTypeList = responseList,
            complete: () => {

            }
          })
        } else if (this.selectedType == "school") {
          this.otherStuffService.getAllTown().subscribe({
            next: response => this.townList = response,
            complete: () => {
              this.filteredTownList = this.townList
              this.getSchoolByTown()
            }
          })
        }
      }
    })


  }

  getSchoolByTown() {
    this.schoolService.getSchoolsBySearch(this.selectedTown).subscribe({
      next: response => this.schoolList = response,
      complete: () => this.filteredSchoolList = this.schoolList
    })
  }

  getInstructorBySearch() {
    this.instructorService.getInstructorBySearch(this.userService.loggedUser()?.school?.id!, this.selectedFuelTypeId).subscribe({
      next: response => {
        this.instructorList = response
        console.log(response)
      },
      complete: () => this.filteredInstructorList = this.instructorList
    })
  }

  navigateToProfilePage() {
    if (this.selectedSchool == null) {
      this.router.navigate(["profil/user", this.selectedInstructor?.instructorUser.id])
    } else {
      this.router.navigate(["profil/school", this.selectedSchool.id])
    }
  }

  getInstructorById(id: number) {
    this.instructorService.getInstructorById(id).subscribe({
      next: response => this.selectedInstructor = response
    })
  }

  filterInstructor(searchedName: string) {
    this.filteredInstructorList = this.instructorList.filter(instructor =>
      instructor.name.replaceAll(" ", "").toLowerCase().substring(0, searchedName.length) === searchedName.replaceAll(" ", "").toLowerCase().trim()
    )
  }

  filterTown() {
    this.filteredTownList = this.townList.filter(town => town.toLowerCase().substring(0, this.selectedTown.trim().length) == this.selectedTown.trim().toLowerCase())
  }

  filterSchoolName(searchedName: string) {
    this.filteredSchoolList = this.schoolList.filter(school => school.name.toLowerCase().trim().substring(0, searchedName.length) === searchedName.trim().toLowerCase())

  }

  getSchoolById(id: number) {
    this.schoolService.getSchoolById(id).subscribe({
      next: response => this.selectedSchool = response,
    })
  }

  sendJoinRequest() {
    if (this.selectedType == "instructor") {
      this.requestService.sendInstructorJoinRequest(this.userService.loggedUser()?.studentId!, this.selectedInstructor?.id!).subscribe({
        error: error => {
          alert("Hiba merült fel!. Kérlek próbáld meg újra.")
        },
        complete: () => {
          alert("Sikeres kérelem küldés.")
        }
      })
    } else if (this.selectedType == "school") {
      this.requestService.sendSchoolJoinRequest(this.selectedSchool?.id!, this.userService.loggedUser()?.id!).subscribe({
        error: error => {
          alert("Hiba merült fel!. Kérlek próbáld meg újra.")
        },
        complete: () => {
          alert("Sikeres kérelem küldés.")
        }
      })
    }
  }
}
