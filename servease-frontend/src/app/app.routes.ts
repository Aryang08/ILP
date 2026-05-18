import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  // ✅ PUBLIC ROUTES
  { path: 'login', loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent) },
  { path: 'reset-password', loadComponent: () => import('./features/auth/reset-password/reset-password.component').then(m => m.ResetPasswordComponent) },

  // ✅ ADMIN ROUTES
  {
    path: 'admin',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN'] },
    children: [
      { path: 'dashboard', loadComponent: () => import('./features/admin/admin-dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent) },
      { path: 'users', loadComponent: () => import('./features/admin/user-management/user-management.component').then(m => m.UserManagementComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // ✅ CUSTOMER ROUTES
  {
    path: 'customer',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['CUSTOMER'] },
    children: [
      { path: 'dashboard', loadComponent: () => import('./features/customer/customer-dashboard/customer-dashboard.component').then(m => m.CustomerDashboardComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // ✅ SUPERVISOR ROUTES
  {
    path: 'supervisor',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['SUPERVISOR'] },
    children: [
      { path: 'dashboard', loadComponent: () => import('./features/supervisor/supervisor-dashboard/supervisor-dashboard.component').then(m => m.SupervisorDashboardComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // ✅ TECHNICIAN ROUTES
  {
    path: 'technician',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['TECHNICIAN'] },
    children: [
      { path: 'dashboard', loadComponent: () => import('./features/technician/technician-dashboard/technician-dashboard.component').then(m => m.TechnicianDashboardComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // ✅ SHARED ROUTES (ANY LOGGED-IN USER)
  {
    path: 'profile',
    canActivate: [authGuard],
    loadComponent: () => import('./features/profile/profile.component').then(m => m.ProfileComponent)
  },

  // ✅ REDIRECTS
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
