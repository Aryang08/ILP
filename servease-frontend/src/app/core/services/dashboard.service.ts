import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) { }

  getTotalCustomers(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-customers`);
  }

  getTotalTechnicians(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-technicians`);
  }

  getTotalServiceRequests(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-requests`);
  }

  getPendingServices(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/pending-services`);
  }

  getInProgressServices(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/in-progress-services`);
  }

  getCompletedServices(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/completed-services`);
  }
}
