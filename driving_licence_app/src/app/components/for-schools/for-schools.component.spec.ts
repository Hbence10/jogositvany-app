import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForSchoolsComponent } from './for-schools.component';

describe('ForSchoolsComponent', () => {
  let component: ForSchoolsComponent;
  let fixture: ComponentFixture<ForSchoolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForSchoolsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForSchoolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
