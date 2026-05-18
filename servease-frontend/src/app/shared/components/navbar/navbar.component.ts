import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <header class="navbar">
      <div class="navbar-left">
        <button class="hamburger" (click)="toggleSidebar.emit()">☰</button>
        <div class="logo" routerLink="/">ServEase</div>
      </div>
      <div class="navbar-right">
        <div class="user-info" *ngIf="currentUser">
          <span class="user-role">{{ currentUser.role }}</span>
          <span class="user-name">{{ currentUser.name }}</span>
          <div class="user-avatar">{{ getInitials(currentUser.name) }}</div>
        </div>
      </div>
    </header>
  `,
  styles: [`
    .navbar {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 60px;
      background: linear-gradient(135deg, #2c4f8a, #1d3557);
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      color: #fff;
      z-index: 1001;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.15);
    }

    .navbar-left {
      display: flex;
      align-items: center;
      gap: 12px;
    }

    .hamburger {
      display: none;
      font-size: 24px;
      background: none;
      border: none;
      color: #fff;
      cursor: pointer;
      padding: 4px 8px;
      border-radius: 4px;
      transition: background 0.2s;
    }

    .hamburger:hover {
      background: rgba(255, 255, 255, 0.15);
    }

    .logo {
      font-size: 22px;
      font-weight: 700;
      letter-spacing: 0.5px;
      cursor: pointer;
    }

    .navbar-right {
      display: flex;
      align-items: center;
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .user-role {
      font-size: 11px;
      background: rgba(255, 255, 255, 0.2);
      padding: 3px 10px;
      border-radius: 12px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .user-name {
      font-size: 14px;
      font-weight: 500;
    }

    .user-avatar {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.25);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      font-weight: 600;
    }

    @media (max-width: 900px) {
      .hamburger { display: block; }
      .user-name { display: none; }
      .user-role { display: none; }
    }
  `]
})
export class NavbarComponent {
  @Output() toggleSidebar = new EventEmitter<void>();

  currentUser: { name: string; role: string } | null = null;

  constructor(private authService: AuthService) {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);
  }
}
