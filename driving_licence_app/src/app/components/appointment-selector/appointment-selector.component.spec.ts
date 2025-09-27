import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentSelectorComponent } from './appointment-selector.component';

describe('AppointmentSelectorComponent', () => {
  let component: AppointmentSelectorComponent;
  let fixture: ComponentFixture<AppointmentSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppointmentSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
