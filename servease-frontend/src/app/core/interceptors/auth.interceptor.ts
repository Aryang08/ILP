import { HttpInterceptorFn } from '@angular/common/http';
export const authInterceptor:HttpInterceptorFn=(req,next)=>{const sid=sessionStorage.getItem('sessionId');return next(sid?req.clone({setHeaders:{sessionId:sid}}):req);};
