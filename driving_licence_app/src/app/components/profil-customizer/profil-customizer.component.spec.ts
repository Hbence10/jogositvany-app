import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilCustomizerComponent } from './profil-customizer.component';

describe('ProfilCustomizerComponent', () => {
  let component: ProfilCustomizerComponent;
  let fixture: ComponentFixture<ProfilCustomizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilCustomizerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfilCustomizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
