import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

@Injectable({providedIn:'root'})
export class AuthService {
  constructor(private http:HttpClient, private router:Router){}
  get sessionId(){return sessionStorage.getItem('sessionId');}
  get role(){return sessionStorage.getItem('role');}
  login(userId:string,password:string){
    return this.http.post<{sessionId:string,role:string}>(`${environment.apiUrl}/auth/login?userId=${userId}&password=${password}`,{});
  }
  saveSession(data:{sessionId:string,role:string}){sessionStorage.setItem('sessionId',data.sessionId);sessionStorage.setItem('role',data.role);}
  logout(){const sid=this.sessionId;sessionStorage.clear(); if(sid){this.http.post(`${environment.apiUrl}/auth/logout`,{}, {headers:{sessionId:sid}}).subscribe();} this.router.navigate(['/login']);}
  validateSession(){return this.http.get(`${environment.apiUrl}/auth/session/validate`);}
}
