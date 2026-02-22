import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSetterComponent } from './admin-setter.component';

describe('AdminSetterComponent', () => {
  let component: AdminSetterComponent;
  let fixture: ComponentFixture<AdminSetterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminSetterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminSetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
