import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({providedIn:'root'})
export class ApiService {
  constructor(private http:HttpClient){}
  getSupervisorDashboard(supervisorId:string){return this.http.get<any>(`${environment.apiUrl}/supervisor/${supervisorId}`);}
  getSupervisorTickets(supervisorId:string){return this.http.get<any[]>(`${environment.apiUrl}/assignment/table?supervisorId=${supervisorId}`);}
  getTechnicians(page=0,size=10){return this.http.get<any>(`${environment.apiUrl}/supervisor/technicians/page?page=${page}&size=${size}`);}
  registerTechnician(supervisorId:string,payload:any){return this.http.post(`${environment.apiUrl}/supervisor/onboard/${supervisorId}`,payload,{responseType:'text'});}
  getCustomerServices(customerId:string){return this.http.get<any[]>(`${environment.apiUrl}/service/customer/${customerId}`);}
  getServiceHistory(serviceId:string){return this.http.get<any[]>(`${environment.apiUrl}/service-history/service/${serviceId}`);}
}
