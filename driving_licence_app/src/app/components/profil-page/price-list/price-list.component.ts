import { Component, inject, input, OnInit, output } from '@angular/core';
import { SchoolCategory } from '../../../models/schoolCategory.model';
import { DrivingLessonService } from '../../../services/driving-lesson.service';
import { DrivingLicenseCategory } from '../../../models/driving-license.model';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsersService } from '../../../services/users.service';
import { SchoolServiceService } from '../../../services/school-service.service';

@Component({
  selector: 'app-price-list',
  imports: [ReactiveFormsModule],
  templateUrl: './price-list.component.html',
  styleUrl: './price-list.component.css'
})
export class PriceListComponent implements OnInit {
  drivingLessonService = inject(DrivingLessonService)
  otherStuffService = inject(OtherStuffServiceService)
  userService = inject(UsersService)
  schoolService = inject(SchoolServiceService)
  schoolId = input.required<number>()
  priceList: SchoolCategory[] = []
  close = output()
  categories: DrivingLicenseCategory[] = []
  form!: FormGroup

  ngOnInit(): void {
    this.drivingLessonService.getDrivingLicenseCategoriesBySchool(this.schoolId()).subscribe({
      next: response => this.priceList = response
    })

    this.otherStuffService.getAllCategory().subscribe({
      next: response => {
        this.categories = response.filter(c =>
          !this.priceList.map(p => p.licenseCategory.id).includes(c.id)
        )
      }
    })

    this.form = new FormGroup({
      category: new FormControl(null, [Validators.required]),
      price: new FormControl(null, [Validators.required])
    })
  }

  addCategory() {
    this.schoolService.addCategory({ categoryId: this.form.controls["category"].value, schoolId: this.schoolId(), price: this.form.controls["price"].value}).subscribe({
      next: response => {
        console.log(response)
        this.priceList.push(response)
        this.categories.filter(c => c.id != this.form.controls["category"].value)
        this.form.reset()
      }
    })
  }

  deleteCategory(id: number) {
    this.schoolService.deleteCategory(id).subscribe({
      next: response => {
        this.categories.push(
          this.priceList[this.priceList.findIndex(p => p.id == id)].licenseCategory
        )
        this.priceList = this.priceList.filter(p => p.id !== id)
      }
    })
  }
}
