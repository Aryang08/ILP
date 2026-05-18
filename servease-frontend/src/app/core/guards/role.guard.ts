<<<<<<< HEAD
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
=======
import { inject } from '@angular/core';import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';import { AuthService } from '../services/auth.service';
export const RoleGuard:CanActivateFn=(route:ActivatedRouteSnapshot)=>{const auth=inject(AuthService);const router=inject(Router);const roles=route.data['roles'] as string[];if(!roles.includes(auth.role||'')){router.navigate(['/profile']);return false;}return true;};
>>>>>>> 1e21f13c135447ae0d68cc7dae3baf6991f05c1f
