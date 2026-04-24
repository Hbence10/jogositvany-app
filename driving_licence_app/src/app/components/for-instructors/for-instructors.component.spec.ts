import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForInstructorsComponent } from './for-instructors.component';

describe('ForInstructorsComponent', () => {
  let component: ForInstructorsComponent;
  let fixture: ComponentFixture<ForInstructorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForInstructorsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForInstructorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
