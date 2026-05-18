import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';
import { DashboardService } from '../../../core/services/dashboard.service';
import { AuthService } from '../../../core/services/auth.service';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, SidebarComponent, StatCardComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>

    <main class="dashboard-content" [class.sidebar-open]="sidebarOpen">
      <h2 class="page-title">Admin Dashboard</h2>

      <div class="stats-grid">
        <app-stat-card label="Total Customers" [value]="totalCustomers" icon="👥" iconBg="#e3f2fd" delay="0.1s"></app-stat-card>
        <app-stat-card label="Total Technicians" [value]="totalTechnicians" icon="🔧" iconBg="#e8f5e9" delay="0.2s"></app-stat-card>
        <app-stat-card label="Total Requests" [value]="totalRequests" icon="📋" iconBg="#fff3e0" delay="0.3s"></app-stat-card>
        <app-stat-card label="Pending" [value]="pending" icon="⏳" iconBg="#fce4ec" delay="0.4s"></app-stat-card>
        <app-stat-card label="In Progress" [value]="inProgress" icon="🔄" iconBg="#e0f7fa" delay="0.5s"></app-stat-card>
        <app-stat-card label="Completed" [value]="completed" icon="✅" iconBg="#e8f5e9" delay="0.6s"></app-stat-card>
      </div>

      <div class="recent-section">
        <div class="section-header">
          <h3>Recent Users</h3>
          <button class="btn-view-all" routerLink="/admin/users">View All →</button>
        </div>
        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let user of recentUsers">
                <td>{{ user.userId }}</td>
                <td>{{ user.name }}</td>
                <td>{{ user.email }}</td>
                <td><span class="role-badge" [attr.data-role]="user.role">{{ user.role }}</span></td>
              </tr>
              <tr *ngIf="recentUsers.length === 0">
                <td colspan="4" class="empty-row">No users found</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>

    <footer class="app-footer">
      <p>© 2026 ServEase Services</p>
    </footer>
  `,
  styles: [`
    .dashboard-content {
      margin-left: 240px;
      padding: 80px 30px 60px;
      min-height: calc(100vh - 60px);
      background: #F8FEFF;
      transition: margin-left 0.3s ease;
    }
    .page-title { font-size: 22px; font-weight: 700; color: #1d3557; margin-bottom: 24px; }
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 20px;
      margin-bottom: 30px;
    }
    .recent-section {
      background: #fff;
      border-radius: 12px;
      padding: 24px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.06);
    }
    .section-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
    }
    .section-header h3 { font-size: 16px; color: #1d3557; margin: 0; }
    .btn-view-all {
      background: none; border: none; color: #2c4f8a; font-size: 13px;
      cursor: pointer; font-weight: 500;
    }
    .btn-view-all:hover { text-decoration: underline; }
    .table-container { overflow-x: auto; }
    table { width: 100%; border-collapse: collapse; min-width: 500px; }
    th { background: #E3F2FD; padding: 10px 14px; text-align: left; font-size: 13px; color: #555; }
    td { padding: 10px 14px; border-bottom: 1px solid #f0f0f0; font-size: 14px; color: #333; }
    .role-badge {
      display: inline-block; padding: 3px 10px; border-radius: 12px;
      font-size: 11px; font-weight: 600; text-transform: uppercase;
    }
    .role-badge[data-role="ADMIN"] { background: #e8efff; color: #2c4f8a; }
    .role-badge[data-role="CUSTOMER"] { background: #e8f5e9; color: #2e7d32; }
    .role-badge[data-role="SUPERVISOR"] { background: #fff3e0; color: #e65100; }
    .role-badge[data-role="TECHNICIAN"] { background: #e0f7fa; color: #00695c; }
    .empty-row { text-align: center; color: #999; padding: 20px !important; }
    .app-footer {
      margin-left: 240px;
      background: #333; color: #fff; text-align: center; padding: 15px; font-size: 13px;
    }
    @media (max-width: 900px) {
      .dashboard-content { margin-left: 0; padding: 80px 16px 60px; }
      .app-footer { margin-left: 0; }
    }
  `]
})
export class AdminDashboardComponent implements OnInit {
  sidebarOpen = true;
  totalCustomers = 0;
  totalTechnicians = 0;
  totalRequests = 0;
  pending = 0;
  inProgress = 0;
  completed = 0;
  recentUsers: User[] = [];

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadStats();
    this.loadRecentUsers();
  }

  loadStats(): void {
    this.dashboardService.getTotalCustomers().subscribe({ next: v => this.totalCustomers = v, error: () => { } });
    this.dashboardService.getTotalTechnicians().subscribe({ next: v => this.totalTechnicians = v, error: () => { } });
    this.dashboardService.getTotalServiceRequests().subscribe({ next: v => this.totalRequests = v, error: () => { } });
    this.dashboardService.getPendingServices().subscribe({ next: v => this.pending = v, error: () => { } });
    this.dashboardService.getInProgressServices().subscribe({ next: v => this.inProgress = v, error: () => { } });
    this.dashboardService.getCompletedServices().subscribe({ next: v => this.completed = v, error: () => { } });
  }

  loadRecentUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => this.recentUsers = users.slice(0, 5),
      error: () => { }
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
