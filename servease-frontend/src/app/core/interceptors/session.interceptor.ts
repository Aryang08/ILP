import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const sessionInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  // Attach sessionId header if available
  const sessionId = sessionStorage.getItem('sessionId');
  if (sessionId) {
    req = req.clone({
      setHeaders: {
        sessionId: sessionId
      }
    });
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Session expired or invalid — clear and redirect to login
        sessionStorage.clear();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};
