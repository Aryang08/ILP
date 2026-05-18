<<<<<<< HEAD
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};
=======
import { inject } from '@angular/core';import { CanActivateFn, Router } from '@angular/router';import { AuthService } from '../services/auth.service';
export const AuthGuard:CanActivateFn=()=>{const auth=inject(AuthService);const router=inject(Router); if(!auth.sessionId){router.navigate(['/login']);return false;} return true;};
>>>>>>> 1e21f13c135447ae0d68cc7dae3baf6991f05c1f
