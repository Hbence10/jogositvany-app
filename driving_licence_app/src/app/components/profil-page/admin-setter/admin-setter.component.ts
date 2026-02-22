import { Component, inject, input, OnInit, output } from '@angular/core';
import { SchoolServiceService } from '../../../services/school-service.service';
import { OtherStuffServiceService } from '../../../services/other-stuff-service.service';

@Component({
  selector: 'app-admin-setter',
  imports: [],
  templateUrl: './admin-setter.component.html',
  styleUrl: './admin-setter.component.css'
})
export class AdminSetterComponent implements OnInit {
  schoolId = input.required<number>()
  schoolService = inject(SchoolServiceService)
  otherStuffService = inject(OtherStuffServiceService)
  close = output()
  emailList: string[] = []
  selectedEmail: string | null = null

  ngOnInit(): void {
   this.otherStuffService.getAllEmail().subscribe({
    next: response => {
      this.emailList = response
    }
   })
  }

  addAdmin() {
    this.schoolService.setAdmin(this.selectedEmail!, this.schoolId()).subscribe({
      next: response => {
        this.close.emit()
      }
    })
  }
}
