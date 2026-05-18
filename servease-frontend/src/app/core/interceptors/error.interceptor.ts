import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';import { catchError, throwError } from 'rxjs';
export const errorInterceptor:HttpInterceptorFn=(req,next)=>next(req).pipe(catchError((err:HttpErrorResponse)=>{if(err.status===401){sessionStorage.clear();}return throwError(()=>err);}));
