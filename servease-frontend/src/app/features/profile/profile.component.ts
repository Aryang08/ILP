import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../core/models/user.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent, SidebarComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>
    <main class="profile-content">
      <h2 class="page-title">My Profile</h2>
      <div class="profile-card">
        <div class="avatar-section">
          <div class="avatar-circle">{{ getInitials() }}</div>
          <h3>{{ user?.name }}</h3>
          <span class="role-badge">{{ user?.role }}</span>
        </div>
        <form [formGroup]="profileForm" (ngSubmit)="onSave()" *ngIf="!isLoading">
          <div class="form-group">
            <label>User ID</label>
            <input [value]="user?.userId || ''" disabled class="disabled-input">
          </div>
          <div class="form-group">
            <label>Name <span class="req">*</span></label>
            <input formControlName="name" placeholder="Full Name">
            <span class="err" *ngIf="profileForm.get('name')?.touched && profileForm.get('name')?.hasError('required')">Required</span>
          </div>
          <div class="form-group">
            <label>Email <span class="req">*</span></label>
            <input formControlName="email" placeholder="email&#64;example.com">
            <span class="err" *ngIf="profileForm.get('email')?.touched && profileForm.get('email')?.hasError('email')">Invalid email</span>
          </div>
          <div class="form-group">
            <label>Phone</label>
            <input formControlName="phone" placeholder="10-digit number">
            <span class="err" *ngIf="profileForm.get('phone')?.touched && profileForm.get('phone')?.hasError('pattern')">Must be 10 digits</span>
          </div>
          <div class="success-msg" *ngIf="successMsg">✅ {{ successMsg }}</div>
          <div class="error-msg-box" *ngIf="errorMsg">⚠️ {{ errorMsg }}</div>
          <button type="submit" class="btn-save" [disabled]="profileForm.invalid || saving">
            {{ saving ? 'Saving...' : 'Save Changes' }}
          </button>
        </form>
        <div *ngIf="isLoading" class="loading">Loading profile...</div>
      </div>
    </main>
    <footer class="app-footer"><p>© 2026 ServEase Services</p></footer>
  `,
  styles: [`
    .profile-content { margin-left: 240px; padding: 80px 30px 60px; min-height: calc(100vh - 60px); background: #F8FEFF; }
    .page-title { font-size: 22px; font-weight: 700; color: #1d3557; margin-bottom: 24px; }
    .profile-card { background: #fff; border-radius: 12px; padding: 30px; max-width: 500px; box-shadow: 0 2px 10px rgba(0,0,0,0.06); }
    .avatar-section { text-align: center; margin-bottom: 24px; }
    .avatar-circle { width: 72px; height: 72px; border-radius: 50%; background: linear-gradient(135deg, #2c4f8a, #457b9d); color: #fff; display: flex; align-items: center; justify-content: center; font-size: 24px; font-weight: 700; margin: 0 auto 12px; }
    .avatar-section h3 { margin: 0; color: #1d3557; }
    .role-badge { display: inline-block; padding: 3px 12px; border-radius: 12px; background: #e8efff; color: #2c4f8a; font-size: 11px; font-weight: 600; margin-top: 6px; text-transform: uppercase; }
    .form-group { margin-bottom: 16px; }
    .form-group label { display: block; font-size: 13px; font-weight: 600; color: #444; margin-bottom: 5px; }
    .req { color: #e74c3c; }
    .form-group input { width: 100%; padding: 10px 12px; border: 1.5px solid #ddd; border-radius: 8px; font-size: 14px; box-sizing: border-box; }
    .form-group input:focus { border-color: #2c4f8a; outline: none; }
    .disabled-input { background: #f5f5f5; color: #999; }
    .err { display: block; color: #e74c3c; font-size: 11px; margin-top: 3px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 10px; border-radius: 8px; font-size: 13px; margin-bottom: 14px; text-align: center; }
    .error-msg-box { background: #fde8e8; color: #c0392b; padding: 10px; border-radius: 8px; font-size: 13px; margin-bottom: 14px; text-align: center; }
    .btn-save { width: 100%; padding: 12px; background: linear-gradient(135deg, #2c4f8a, #1d3557); color: #fff; border: none; border-radius: 8px; font-size: 14px; font-weight: 600; cursor: pointer; }
    .btn-save:disabled { opacity: 0.5; cursor: not-allowed; }
    .loading { text-align: center; color: #888; padding: 40px; }
    .app-footer { margin-left: 240px; background: #333; color: #fff; text-align: center; padding: 15px; font-size: 13px; }
    @media (max-width: 900px) { .profile-content { margin-left: 0; } .app-footer { margin-left: 0; } }
  `]
})
export class ProfileComponent implements OnInit {
  sidebarOpen = true;
  user: User | null = null;
  isLoading = true;
  saving = false;
  successMsg = '';
  errorMsg = '';

  profileForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.pattern(/^\d{10}$/)])
  });

  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (user) => {
        this.user = user;
        this.profileForm.patchValue({ name: user.name, email: user.email, phone: user.phone });
        this.isLoading = false;
      },
      error: () => { this.isLoading = false; this.errorMsg = 'Failed to load profile'; }
    });
  }

  getInitials(): string {
    return this.user?.name?.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2) || '?';
  }

  onSave(): void {
    if (this.profileForm.invalid) return;
    this.saving = true;
    this.successMsg = '';
    this.errorMsg = '';

    const updates = this.profileForm.value as { name?: string; email?: string; phone?: string };
    this.userService.updateProfile(updates).subscribe({
      next: (res) => {
        this.saving = false;
        this.successMsg = 'Profile updated successfully!';
        if (updates.name) { sessionStorage.setItem('name', updates.name); }
        if (updates.email) { sessionStorage.setItem('email', updates.email); }
        setTimeout(() => this.successMsg = '', 3000);
      },
      error: (err) => { this.saving = false; this.errorMsg = err.error?.message || 'Update failed'; }
    });
  }

  logout(): void { this.authService.logout(); }
}
