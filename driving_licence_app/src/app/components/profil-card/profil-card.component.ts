import { Component, inject, input } from '@angular/core';
import { Router } from 'express';
import { ProfileCard } from '../../models/notEntity/profileCard.model';

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

  ngOnInit(): void {
    console.log(this.profilCardData())
    console.log(this.role())
  }

  checkProfile(){
    this.router.navigate(["profil", "user", this.profilCardData().id])
  }
}
