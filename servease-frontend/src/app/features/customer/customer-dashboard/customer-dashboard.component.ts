import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, NavbarComponent, SidebarComponent, StatCardComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>

    <main class="dashboard-content">
      <h2 class="page-title">Welcome, {{ userName }}!</h2>
      <p class="page-subtitle">Your Customer Dashboard</p>

      <div class="stats-grid">
        <app-stat-card label="My Appliances" value="–" icon="📱" iconBg="#e3f2fd" delay="0.1s"></app-stat-card>
        <app-stat-card label="Active Requests" value="–" icon="📋" iconBg="#fff3e0" delay="0.2s"></app-stat-card>
        <app-stat-card label="Completed Services" value="–" icon="✅" iconBg="#e8f5e9" delay="0.3s"></app-stat-card>
      </div>

      <div class="quick-actions">
        <h3>Quick Actions</h3>
        <div class="action-grid">
          <div class="action-card">
            <span class="action-icon">🔧</span>
            <p>Request Service</p>
          </div>
          <div class="action-card">
            <span class="action-icon">📱</span>
            <p>My Appliances</p>
          </div>
          <div class="action-card">
            <span class="action-icon">📜</span>
            <p>Service History</p>
          </div>
          <div class="action-card">
            <span class="action-icon">⭐</span>
            <p>Give Feedback</p>
          </div>
        </div>
      </div>
    </main>

    <footer class="app-footer"><p>© 2026 ServEase Services</p></footer>
  `,
  styles: [`
    .dashboard-content {
      margin-left: 240px; padding: 80px 30px 60px;
      min-height: calc(100vh - 60px); background: #F8FEFF;
    }
    .page-title { font-size: 22px; font-weight: 700; color: #1d3557; margin: 0; }
    .page-subtitle { color: #888; font-size: 14px; margin: 4px 0 24px; }
    .stats-grid {
      display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 20px; margin-bottom: 30px;
    }
    .quick-actions { background: #fff; border-radius: 12px; padding: 24px; box-shadow: 0 2px 10px rgba(0,0,0,0.06); }
    .quick-actions h3 { font-size: 16px; color: #1d3557; margin: 0 0 16px; }
    .action-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(140px, 1fr)); gap: 16px; }
    .action-card {
      background: #f8fbff; border: 1.5px solid #e8efff; border-radius: 12px;
      padding: 20px; text-align: center; cursor: pointer; transition: all 0.2s;
    }
    .action-card:hover { border-color: #2c4f8a; background: #e8efff; transform: translateY(-2px); }
    .action-icon { font-size: 28px; display: block; margin-bottom: 8px; }
    .action-card p { margin: 0; font-size: 13px; color: #555; font-weight: 500; }
    .app-footer { margin-left: 240px; background: #333; color: #fff; text-align: center; padding: 15px; font-size: 13px; }
    @media (max-width: 900px) {
      .dashboard-content { margin-left: 0; padding: 80px 16px 60px; }
      .app-footer { margin-left: 0; }
    }
  `]
})
export class CustomerDashboardComponent {
  sidebarOpen = true;
  userName = sessionStorage.getItem('name') || 'Customer';

  constructor(private authService: AuthService) { }

  logout(): void { this.authService.logout(); }
}
