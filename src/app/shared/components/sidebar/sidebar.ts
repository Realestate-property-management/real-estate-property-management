import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class Sidebar {

  @Input() collapsed = false;
  @Input() mobileOpen = false;

  @Output() toggleSidebar = new EventEmitter<void>();
  @Output() closeMobile = new EventEmitter<void>();

  closeMenu(): void {
    this.closeMobile.emit();
  }
}