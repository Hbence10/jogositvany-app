import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NavbarPhoneComponent } from './components/navbar-phone/navbar-phone.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,FooterComponent,NavbarComponent, NavbarPhoneComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'driving_licence_app';
}
