import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewWriterComponent } from './review-writer.component';

describe('ReviewWriterComponent', () => {
  let component: ReviewWriterComponent;
  let fixture: ComponentFixture<ReviewWriterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewWriterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewWriterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
