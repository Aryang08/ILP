import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-supervisor-dashboard',
  standalone: true,
  imports: [CommonModule, NavbarComponent, SidebarComponent, StatCardComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>

    <main class="dashboard-content">
      <h2 class="page-title">Supervisor Dashboard</h2>
      <p class="page-subtitle">Welcome back, {{ userName }}</p>

      <div class="stats-grid">
        <app-stat-card label="Total Open" value="–" icon="📂" iconBg="#e3f2fd" delay="0.1s"></app-stat-card>
        <app-stat-card label="Pending Assignment" value="–" icon="⏳" iconBg="#fff3e0" delay="0.2s"></app-stat-card>
        <app-stat-card label="In Progress" value="–" icon="🔄" iconBg="#e0f7fa" delay="0.3s"></app-stat-card>
        <app-stat-card label="Delayed" value="–" icon="⚠️" iconBg="#fce4ec" delay="0.4s"></app-stat-card>
      </div>

      <div class="dashboard-grid">
        <div class="table-box">
          <h3>Technician Availability</h3>
          <table>
            <thead><tr><th>Technician</th><th>Status</th><th>Jobs</th></tr></thead>
            <tbody>
              <tr><td>Rajesh Kumar</td><td><span class="status available">Available</span></td><td>2</td></tr>
              <tr><td>Nikhil Sharma</td><td><span class="status busy">Busy</span></td><td>4</td></tr>
              <tr><td>Ankit Verma</td><td><span class="status leave">On Leave</span></td><td>0</td></tr>
            </tbody>
          </table>
        </div>

        <div class="table-box">
          <h3>Today's Schedule</h3>
          <table>
            <thead><tr><th>Technician</th><th>Task</th><th>Time</th></tr></thead>
            <tbody>
              <tr><td>Rajesh Kumar</td><td>LG Washing Machine</td><td>10:00 AM</td></tr>
              <tr><td>Sunita Raj</td><td>AC Repair</td><td>10:30 AM</td></tr>
              <tr><td>Anil Desai</td><td>Microwave</td><td>11:00 AM</td></tr>
            </tbody>
          </table>
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
      display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 20px; margin-bottom: 30px;
    }
    .dashboard-grid {
      display: grid; grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
      gap: 20px;
    }
    .table-box {
      background: #fff; border-radius: 12px; padding: 20px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.06);
    }
    .table-box h3 { font-size: 15px; color: #1d3557; margin: 0 0 14px; }
    table { width: 100%; border-collapse: collapse; }
    th { background: #E3F2FD; padding: 10px 12px; text-align: left; font-size: 12px; color: #555; }
    td { padding: 10px 12px; border-bottom: 1px solid #f0f0f0; font-size: 13px; }
    .status {
      display: inline-block; padding: 2px 8px; border-radius: 10px;
      font-size: 11px; font-weight: 600;
    }
    .status.available { background: #e8f5e9; color: #2e7d32; }
    .status.busy { background: #fff3e0; color: #e65100; }
    .status.leave { background: #fce4ec; color: #c62828; }
    .app-footer { margin-left: 240px; background: #333; color: #fff; text-align: center; padding: 15px; font-size: 13px; }
    @media (max-width: 900px) {
      .dashboard-content { margin-left: 0; padding: 80px 16px 60px; }
      .dashboard-grid { grid-template-columns: 1fr; }
      .app-footer { margin-left: 0; }
    }
  `]
})
export class SupervisorDashboardComponent {
  sidebarOpen = true;
  userName = sessionStorage.getItem('name') || 'Supervisor';

  constructor(private authService: AuthService) { }

  logout(): void { this.authService.logout(); }
}
