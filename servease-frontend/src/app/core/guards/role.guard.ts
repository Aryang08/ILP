import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRoles: string[] = route.data?.['roles'] || [];
  const userRole = authService.getRole();

  if (!userRole || !expectedRoles.includes(userRole)) {
    // Redirect to their own dashboard if logged in, else login
    if (authService.isLoggedIn()) {
      router.navigate([authService.getDashboardRoute()]);
    } else {
      router.navigate(['/login']);
    }
    return false;
  }

  return true;
};
