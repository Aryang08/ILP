import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-technician-dashboard',
  standalone: true,
  imports: [CommonModule, NavbarComponent, SidebarComponent, StatCardComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>
    <main class="dashboard-content">
      <h2 class="page-title">Technician Dashboard</h2>
      <p class="page-subtitle">Welcome back, {{ userName }}</p>
      <div class="stats-grid">
        <app-stat-card label="Assigned Jobs" value="–" icon="📋" iconBg="#e3f2fd" delay="0.1s"></app-stat-card>
        <app-stat-card label="In Progress" value="–" icon="🔧" iconBg="#fff3e0" delay="0.2s"></app-stat-card>
        <app-stat-card label="Completed Today" value="–" icon="✅" iconBg="#e8f5e9" delay="0.3s"></app-stat-card>
      </div>
      <div class="schedule-section">
        <h3>Today's Schedule</h3>
        <div class="schedule-cards">
          <div class="schedule-card"><div class="schedule-time">10:00 AM</div><div class="schedule-info"><strong>AC Repair</strong><p>Customer: John Doe</p></div></div>
          <div class="schedule-card"><div class="schedule-time">11:30 AM</div><div class="schedule-info"><strong>Washing Machine</strong><p>Customer: Jane Smith</p></div></div>
          <div class="schedule-card"><div class="schedule-time">02:00 PM</div><div class="schedule-info"><strong>Refrigerator Repair</strong><p>Customer: Amit Patel</p></div></div>
        </div>
      </div>
    </main>
    <footer class="app-footer"><p>© 2026 ServEase Services</p></footer>
  `,
  styles: [`
    .dashboard-content { margin-left: 240px; padding: 80px 30px 60px; min-height: calc(100vh - 60px); background: #F8FEFF; }
    .page-title { font-size: 22px; font-weight: 700; color: #1d3557; margin: 0; }
    .page-subtitle { color: #888; font-size: 14px; margin: 4px 0 24px; }
    .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px; }
    .schedule-section { background: #fff; border-radius: 12px; padding: 24px; box-shadow: 0 2px 10px rgba(0,0,0,0.06); }
    .schedule-section h3 { font-size: 16px; color: #1d3557; margin: 0 0 16px; }
    .schedule-cards { display: flex; flex-direction: column; gap: 12px; }
    .schedule-card { display: flex; gap: 16px; padding: 16px; background: #f8fbff; border: 1px solid #e8efff; border-radius: 10px; }
    .schedule-card:hover { border-color: #2c4f8a; }
    .schedule-time { background: #e8efff; color: #2c4f8a; padding: 8px 12px; border-radius: 8px; font-weight: 600; font-size: 13px; white-space: nowrap; }
    .schedule-info strong { color: #1d3557; font-size: 14px; }
    .schedule-info p { margin: 4px 0 0; color: #888; font-size: 13px; }
    .app-footer { margin-left: 240px; background: #333; color: #fff; text-align: center; padding: 15px; font-size: 13px; }
    @media (max-width: 900px) { .dashboard-content { margin-left: 0; } .app-footer { margin-left: 0; } }
  `]
})
export class TechnicianDashboardComponent {
  sidebarOpen = true;
  userName = sessionStorage.getItem('name') || 'Technician';
  constructor(private authService: AuthService) { }
  logout(): void { this.authService.logout(); }
}
