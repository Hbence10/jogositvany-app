import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FuelType } from '../../models/fuel-type.model';
import { Instructors } from '../../models/instructors.model';
import { School } from '../../models/school.model';
import { OtherStuffServiceService } from '../../services/other-stuff-service.service';
import { SchoolServiceService } from '../../services/school-service.service';
import { InstructorServiceService } from '../../services/instructor-service.service';

@Component({
  selector: 'app-search-page',
  imports: [],
  templateUrl: './search-page.component.html',
  styleUrl: './search-page.component.css'
})
export class SearchPageComponent implements OnInit {
  private otherStuffService = inject(OtherStuffServiceService)
  private schoolService = inject(SchoolServiceService)
  private instructorService = inject(InstructorServiceService)
  private route = inject(ActivatedRoute)
  private destroyRef = inject(DestroyRef)
  fuelTypeList: FuelType[] = []
  townList: string[] = []
  selectedFuelType!: FuelType
  selectedType: string = ""
  selectedTown: string = "Budapest"

  selectedSchool: School | null = null
  selectedInstructor: Instructors | null = null


  ngOnInit(): void {
    let subscription;

    this.route.params.subscribe({
      next: param => {
        this.selectedType = param["type"]
        if (this.selectedType == "instructor") {
          subscription = this.otherStuffService.getAllFuelType().subscribe({
            next: responseList => this.fuelTypeList = responseList
          })
        } else if (this.selectedType == "school") {
          subscription = this.otherStuffService.getAllTown().subscribe({
            next: response => this.townList = response
          })
        }
      }
    })



    this.destroyRef.onDestroy(() => {
      // subscription.unscrubsibe()
    })
  }

  selectSchool(){

  }

  selectInstructor(){

  }
}
