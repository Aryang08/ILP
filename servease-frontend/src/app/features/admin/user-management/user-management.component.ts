import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { NavbarComponent } from '../../../shared/components/navbar/navbar.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent, SidebarComponent],
  template: `
    <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
    <app-sidebar [isOpen]="sidebarOpen" (logoutClicked)="logout()"></app-sidebar>

    <main class="content" [class.sidebar-open]="sidebarOpen">
      <div class="content-header">
        <h2>User Management</h2>
        <button class="btn-add" (click)="openModal('create')">+ Add User</button>
      </div>

      <!-- SEARCH -->
      <div class="toolbar">
        <input type="text" placeholder="Search by name, email, or role..." [(ngModel)]="searchTerm"
               (input)="filterUsers()" class="search-input" [formControl]="searchControl">
        <select [formControl]="roleFilter" (change)="filterUsers()">
          <option value="">All Roles</option>
          <option value="ADMIN">Admin</option>
          <option value="SUPERVISOR">Supervisor</option>
          <option value="TECHNICIAN">Technician</option>
          <option value="CUSTOMER">Customer</option>
        </select>
      </div>

      <!-- TABLE -->
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let user of filteredUsers" class="table-row">
              <td>{{ user.userId }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.phone }}</td>
              <td><span class="role-badge" [attr.data-role]="user.role">{{ user.role }}</span></td>
              <td class="actions">
                <button class="btn-edit" (click)="openModal('edit', user)">✏️</button>
                <button class="btn-delete" (click)="confirmDelete(user)">🗑️</button>
              </td>
            </tr>
            <tr *ngIf="filteredUsers.length === 0">
              <td colspan="6" class="empty-row">No users found</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- TOAST -->
      <div class="toast" *ngIf="toastMessage" [class.toast-error]="toastType === 'error'">
        {{ toastMessage }}
      </div>

      <!-- MODAL -->
      <div class="modal-overlay" *ngIf="showModal" (click)="closeModal()">
        <div class="modal-content" (click)="$event.stopPropagation()">
          <h3>{{ modalMode === 'create' ? 'Add New User' : 'Edit User' }}</h3>

          <form [formGroup]="userForm">
            <div class="form-group" *ngIf="modalMode === 'create'">
              <label>User ID <span class="req">*</span></label>
              <input formControlName="userId" placeholder="e.g. u100">
              <span class="err" *ngIf="userForm.get('userId')?.touched && userForm.get('userId')?.hasError('required')">Required</span>
            </div>

            <div class="form-group">
              <label>Name <span class="req">*</span></label>
              <input formControlName="name" placeholder="Full Name">
              <span class="err" *ngIf="userForm.get('name')?.touched && userForm.get('name')?.hasError('required')">Required</span>
            </div>

            <div class="form-group">
              <label>Email <span class="req">*</span></label>
              <input formControlName="email" placeholder="email&#64;example.com">
              <span class="err" *ngIf="userForm.get('email')?.touched && userForm.get('email')?.hasError('email')">Invalid email</span>
            </div>

            <div class="form-group">
              <label>Phone</label>
              <input formControlName="phone" placeholder="10-digit number">
              <span class="err" *ngIf="userForm.get('phone')?.touched && userForm.get('phone')?.hasError('pattern')">Must be 10 digits</span>
            </div>

            <div class="form-group">
              <label>Role <span class="req">*</span></label>
              <select formControlName="role">
                <option value="">Select Role</option>
                <option value="ADMIN">Admin</option>
                <option value="SUPERVISOR">Supervisor</option>
                <option value="TECHNICIAN">Technician</option>
                <option value="CUSTOMER">Customer</option>
              </select>
              <span class="err" *ngIf="userForm.get('role')?.touched && userForm.get('role')?.hasError('required')">Required</span>
            </div>

            <div class="modal-actions">
              <button class="btn-cancel" (click)="closeModal()">Cancel</button>
              <button class="btn-save" (click)="saveUser()" [disabled]="userForm.invalid">
                {{ modalMode === 'create' ? 'Create' : 'Update' }}
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- DELETE CONFIRM -->
      <div class="modal-overlay" *ngIf="showDeleteConfirm" (click)="showDeleteConfirm = false">
        <div class="modal-content delete-modal" (click)="$event.stopPropagation()">
          <h3>Confirm Delete</h3>
          <p>Are you sure you want to delete user <strong>{{ deleteTarget?.name }}</strong>?</p>
          <div class="modal-actions">
            <button class="btn-cancel" (click)="showDeleteConfirm = false">Cancel</button>
            <button class="btn-delete-confirm" (click)="deleteUser()">Delete</button>
          </div>
        </div>
      </div>
    </main>

    <footer class="app-footer"><p>© 2026 ServEase Services</p></footer>
  `,
  styles: [`
    .content {
      margin-left: 240px; padding: 80px 30px 60px;
      min-height: calc(100vh - 60px); background: #F8FEFF;
    }
    .content-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
    .content-header h2 { font-size: 22px; color: #1d3557; margin: 0; }
    .btn-add {
      background: linear-gradient(135deg, #2c4f8a, #1d3557); color: #fff; border: none;
      padding: 10px 20px; border-radius: 8px; font-size: 14px; font-weight: 600; cursor: pointer;
    }
    .btn-add:hover { box-shadow: 0 4px 12px rgba(44, 79, 138, 0.3); }
    .toolbar { display: flex; gap: 12px; margin-bottom: 20px; flex-wrap: wrap; }
    .search-input, .toolbar select {
      padding: 9px 14px; border: 1.5px solid #ddd; border-radius: 8px; font-size: 14px;
    }
    .search-input { flex: 1; min-width: 200px; }
    .search-input:focus, .toolbar select:focus { border-color: #2c4f8a; outline: none; }
    .table-container {
      background: #fff; border-radius: 12px; overflow-x: auto;
      box-shadow: 0 2px 10px rgba(0,0,0,0.06);
    }
    table { width: 100%; border-collapse: collapse; min-width: 700px; }
    th { background: #E3F2FD; padding: 12px 14px; text-align: left; font-size: 13px; color: #555; }
    td { padding: 12px 14px; border-bottom: 1px solid #f0f0f0; font-size: 14px; }
    .table-row:hover { background: #f8fbff; }
    .role-badge {
      display: inline-block; padding: 3px 10px; border-radius: 12px;
      font-size: 11px; font-weight: 600; text-transform: uppercase;
    }
    .role-badge[data-role="ADMIN"] { background: #e8efff; color: #2c4f8a; }
    .role-badge[data-role="CUSTOMER"] { background: #e8f5e9; color: #2e7d32; }
    .role-badge[data-role="SUPERVISOR"] { background: #fff3e0; color: #e65100; }
    .role-badge[data-role="TECHNICIAN"] { background: #e0f7fa; color: #00695c; }
    .actions { display: flex; gap: 8px; }
    .btn-edit, .btn-delete {
      background: none; border: none; cursor: pointer; font-size: 16px; padding: 4px;
      border-radius: 4px; transition: background 0.2s;
    }
    .btn-edit:hover { background: #e8efff; }
    .btn-delete:hover { background: #fde8e8; }
    .empty-row { text-align: center; color: #999; padding: 24px !important; }

    /* TOAST */
    .toast {
      position: fixed; bottom: 24px; right: 24px; background: #2e7d32; color: #fff;
      padding: 12px 24px; border-radius: 8px; font-size: 14px; z-index: 3000;
      animation: slideIn 0.3s ease;
    }
    .toast-error { background: #c0392b; }

    /* MODAL */
    .modal-overlay {
      position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(0,0,0,0.5); display: flex; align-items: center;
      justify-content: center; z-index: 2000;
    }
    .modal-content {
      background: #fff; border-radius: 12px; padding: 30px; width: 100%;
      max-width: 440px; box-shadow: 0 20px 60px rgba(0,0,0,0.25);
    }
    .modal-content h3 { color: #1d3557; margin: 0 0 20px; font-size: 18px; }
    .form-group { margin-bottom: 16px; }
    .form-group label { display: block; font-size: 13px; font-weight: 600; color: #444; margin-bottom: 5px; }
    .req { color: #e74c3c; }
    .form-group input, .form-group select {
      width: 100%; padding: 10px 12px; border: 1.5px solid #ddd; border-radius: 8px;
      font-size: 14px; box-sizing: border-box;
    }
    .form-group input:focus, .form-group select:focus { border-color: #2c4f8a; outline: none; }
    .err { display: block; color: #e74c3c; font-size: 11px; margin-top: 3px; }
    .modal-actions { display: flex; gap: 12px; justify-content: flex-end; margin-top: 20px; }
    .btn-cancel { background: #f0f0f0; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
    .btn-save {
      background: #2c4f8a; color: #fff; border: none; padding: 10px 20px;
      border-radius: 8px; cursor: pointer; font-weight: 600;
    }
    .btn-save:disabled { opacity: 0.5; cursor: not-allowed; }
    .delete-modal p { color: #555; font-size: 14px; margin-bottom: 20px; }
    .btn-delete-confirm {
      background: #e74c3c; color: #fff; border: none; padding: 10px 20px;
      border-radius: 8px; cursor: pointer; font-weight: 600;
    }
    .app-footer {
      margin-left: 240px; background: #333; color: #fff; text-align: center;
      padding: 15px; font-size: 13px;
    }
    @keyframes slideIn { from { transform: translateX(100px); opacity: 0; } to { transform: translateX(0); opacity: 1; } }
    @media (max-width: 900px) {
      .content { margin-left: 0; padding: 80px 16px 60px; }
      .app-footer { margin-left: 0; }
    }
  `]
})
export class UserManagementComponent implements OnInit {
  sidebarOpen = true;
  users: User[] = [];
  filteredUsers: User[] = [];
  searchTerm = '';
  searchControl = new FormControl('');
  roleFilter = new FormControl('');

  showModal = false;
  modalMode: 'create' | 'edit' = 'create';
  showDeleteConfirm = false;
  deleteTarget: User | null = null;

  toastMessage = '';
  toastType: 'success' | 'error' = 'success';

  userForm = new FormGroup({
    userId: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.pattern(/^\d{10}$/)]),
    role: new FormControl('', [Validators.required])
  });

  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => { this.users = users; this.filterUsers(); },
      error: () => this.showToast('Failed to load users', 'error')
    });
  }

  filterUsers(): void {
    const term = this.searchControl.value?.toLowerCase() || '';
    const role = this.roleFilter.value || '';

    this.filteredUsers = this.users.filter(u => {
      const matchTerm = !term ||
        u.name.toLowerCase().includes(term) ||
        u.email.toLowerCase().includes(term) ||
        u.userId.toLowerCase().includes(term);
      const matchRole = !role || u.role === role;
      return matchTerm && matchRole;
    });
  }

  openModal(mode: 'create' | 'edit', user?: User): void {
    this.modalMode = mode;
    this.showModal = true;

    if (mode === 'edit' && user) {
      this.userForm.patchValue(user);
      this.userForm.get('userId')?.disable();
    } else {
      this.userForm.reset();
      this.userForm.get('userId')?.enable();
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.userForm.reset();
    this.userForm.get('userId')?.enable();
  }

  saveUser(): void {
    if (this.userForm.invalid) return;

    const raw = this.userForm.getRawValue();
    const user: User = {
      userId: raw.userId || '',
      name: raw.name || '',
      email: raw.email || '',
      phone: raw.phone || '',
      role: raw.role || ''
    };

    if (this.modalMode === 'create') {
      this.userService.createUser(user).subscribe({
        next: () => { this.showToast('User created successfully'); this.closeModal(); this.loadUsers(); },
        error: (err) => this.showToast(err.error || 'Create failed', 'error')
      });
    } else {
      this.userService.updateUser(user).subscribe({
        next: () => { this.showToast('User updated successfully'); this.closeModal(); this.loadUsers(); },
        error: (err) => this.showToast(err.error?.message || 'Update failed', 'error')
      });
    }
  }

  confirmDelete(user: User): void {
    this.deleteTarget = user;
    this.showDeleteConfirm = true;
  }

  deleteUser(): void {
    if (!this.deleteTarget) return;
    this.userService.deleteUser(this.deleteTarget.userId).subscribe({
      next: () => { this.showToast('User deleted'); this.showDeleteConfirm = false; this.loadUsers(); },
      error: (err) => this.showToast(err.error || 'Delete failed', 'error')
    });
  }

  logout(): void { this.authService.logout(); }

  private showToast(msg: string, type: 'success' | 'error' = 'success'): void {
    this.toastMessage = msg;
    this.toastType = type;
    setTimeout(() => this.toastMessage = '', 3000);
  }
}
