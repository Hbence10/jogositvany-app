import { Component, inject, input } from '@angular/core';
import { ProfileCard } from '../../models/notEntity/profileCard.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profil-card',
  imports: [],
  templateUrl: './profil-card.component.html',
  styleUrl: './profil-card.component.css'
})
export class ProfilCardComponent {
  private router = inject(Router)
  profilCardData = input.required<ProfileCard>()
  role = input.required<string>()

  checkProfile(){
    this.router.navigate(["profil", "user", this.profilCardData().id])
  }
}
