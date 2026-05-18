import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <aside class="sidebar" [class.open]="isOpen">
      <nav class="menu">
        <ul>
          <li *ngFor="let item of menuItems"
              [class.active]="isActive(item.route)"
              (click)="navigateTo(item.route)">
            <span class="menu-icon">{{ item.icon }}</span>
            <span class="menu-label">{{ item.label }}</span>
          </li>
        </ul>
      </nav>
      <div class="sidebar-footer">
        <div class="logout-btn" (click)="onLogout()">
          <span class="menu-icon">🚪</span>
          <span class="menu-label">Logout</span>
        </div>
      </div>
    </aside>
  `,
  styles: [`
    .sidebar {
      position: fixed;
      top: 60px;
      left: 0;
      width: 240px;
      height: calc(100vh - 60px);
      background: #ffffff;
      border-right: 1px solid #e0e0e0;
      display: flex;
      flex-direction: column;
      z-index: 999;
      transition: transform 0.3s ease;
      overflow-y: auto;
    }

    .menu {
      flex: 1;
      padding: 15px 0;
    }

    .menu ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .menu li {
      display: flex;
      align-items: center;
      padding: 12px 20px;
      margin: 4px 10px;
      border-radius: 8px;
      cursor: pointer;
      color: #555;
      font-size: 14px;
      transition: all 0.2s ease;
    }

    .menu li:hover {
      background: #E0F7FA;
      color: #1a73e8;
    }

    .menu li.active {
      background: #e8efff;
      color: #2f5fcd;
      font-weight: 600;
      border-right: 3px solid #2f5fcd;
    }

    .menu-icon {
      margin-right: 12px;
      font-size: 18px;
    }

    .sidebar-footer {
      padding: 15px;
      border-top: 1px solid #eee;
    }

    .logout-btn {
      display: flex;
      align-items: center;
      padding: 12px 20px;
      border-radius: 8px;
      cursor: pointer;
      color: #e74c3c;
      font-weight: 500;
      transition: all 0.2s ease;
    }

    .logout-btn:hover {
      background: #fde8e8;
    }

    @media (max-width: 900px) {
      .sidebar {
        transform: translateX(-100%);
      }
      .sidebar.open {
        transform: translateX(0);
      }
    }
  `]
})
export class SidebarComponent {
  @Input() isOpen = true;
  @Output() logoutClicked = new EventEmitter<void>();

  menuItems: { label: string; route: string; icon: string }[] = [];

  constructor(private authService: AuthService, private router: Router) {
    this.loadMenuItems();
  }

  private loadMenuItems(): void {
    const role = this.authService.getRole();

    switch (role) {
      case 'ADMIN':
        this.menuItems = [
          { label: 'Dashboard', route: '/admin/dashboard', icon: '📊' },
          { label: 'User Management', route: '/admin/users', icon: '👥' },
          { label: 'Profile', route: '/profile', icon: '👤' }
        ];
        break;
      case 'CUSTOMER':
        this.menuItems = [
          { label: 'Dashboard', route: '/customer/dashboard', icon: '📊' },
          { label: 'Profile', route: '/profile', icon: '👤' }
        ];
        break;
      case 'SUPERVISOR':
        this.menuItems = [
          { label: 'Dashboard', route: '/supervisor/dashboard', icon: '📊' },
          { label: 'Profile', route: '/profile', icon: '👤' }
        ];
        break;
      case 'TECHNICIAN':
        this.menuItems = [
          { label: 'Dashboard', route: '/technician/dashboard', icon: '📊' },
          { label: 'Profile', route: '/profile', icon: '👤' }
        ];
        break;
      default:
        this.menuItems = [];
    }
  }

  isActive(route: string): boolean {
    return this.router.url === route;
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  onLogout(): void {
    this.logoutClicked.emit();
  }
}
