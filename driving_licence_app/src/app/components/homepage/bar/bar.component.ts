import { Component, input } from '@angular/core';

@Component({
  selector: 'app-bar',
  imports: [],
  templateUrl: './bar.component.html',
  styleUrl: './bar.component.css',
})
export class BarComponent {
  required = input.required<number | undefined>();
  property1 = input.required<number | undefined>();
  property2 = input<number>(0);

  filledPlace: number = 0;
  isTooMuch: boolean = false;

  ngOnInit(): void {
    let onePercent = this.required()! / 100.0;
    let prop = this.property1();
    this.filledPlace = Math.round(prop! / onePercent);

    if (this.property1()! > this.required()!) {
      this.isTooMuch = true;
      prop! -= this.required()!;
      this.filledPlace = Math.round(prop! / onePercent);
    }

    console.log(this.filledPlace)
    console.log(this.isTooMuch);
  }
}
