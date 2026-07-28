import { Component, EventEmitter, Output } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  @Output() menuClick = new EventEmitter<void>();

  searchOpen = false;
  profileOpen = false;

  toggleSearch(): void {
    this.searchOpen = !this.searchOpen;
  }

  toggleProfile(): void {
    this.profileOpen = !this.profileOpen;
  }
}