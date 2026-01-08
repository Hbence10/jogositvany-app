import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrivingLessonEditorComponent } from './driving-lesson-editor.component';

describe('DrivingLessonEditorComponent', () => {
  let component: DrivingLessonEditorComponent;
  let fixture: ComponentFixture<DrivingLessonEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DrivingLessonEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrivingLessonEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
