import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistartionPageComponent } from './registartion-page.component';

describe('RegistartionPageComponent', () => {
  let component: RegistartionPageComponent;
  let fixture: ComponentFixture<RegistartionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistartionPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistartionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
