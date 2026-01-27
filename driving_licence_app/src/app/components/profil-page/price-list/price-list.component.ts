import { Component, inject, input, OnInit, output } from '@angular/core';
import { SchoolCategory } from '../../../models/schoolCategory.model';
import { DrivingLessonService } from '../../../services/driving-lesson.service';

@Component({
  selector: 'app-price-list',
  imports: [],
  templateUrl: './price-list.component.html',
  styleUrl: './price-list.component.css'
})
export class PriceListComponent implements OnInit {
  drivingLessonService = inject(DrivingLessonService)
  schoolId = input.required<number>()
  priceList: SchoolCategory[] = []
  close = output()

  ngOnInit(): void {
    this.drivingLessonService.getDrivingLicenseCategoriesBySchool(this.schoolId()).subscribe({
      next: response => this.priceList = response
    })
  }
}
