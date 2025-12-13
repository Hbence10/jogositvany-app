import { Component } from '@angular/core';

@Component({
  selector: 'app-calendar',
  imports: [],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent {
  // days: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];
  // day: any = { monday: [1] , tuesday: [1, 2, 3], wednesday: [1, 2], thursday: [1, 2], friday: [1, 2], saturday: [1, 2], sunday: [1, 2] };
  days: {name: string, lessons: number[]}[] = [
    { name: 'Hétfő', lessons: [1] },
    { name: 'Kedd', lessons: [1, 2, 3] },
    { name: 'Szerda', lessons: [1, 2] },
    { name: 'Csütörtök', lessons: [1, 2] },
    { name: 'Péntek', lessons: [1, 2] },
    { name: 'Szombat', lessons: [1, 2] },
    { name: 'Vasárnap', lessons: [1, 2] }
  ]
}
