import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) { }

  // ✅ GET ALL USERS (ADMIN)
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  // ✅ GET USER BY ID
  getUserById(userId: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${userId}`);
  }

  // ✅ CREATE USER
  createUser(user: User): Observable<string> {
    return this.http.post(`${this.apiUrl}`, user, { responseType: 'text' });
  }

  // ✅ UPDATE USER
  updateUser(user: User): Observable<string> {
    return this.http.put(`${this.apiUrl}`, user, { responseType: 'text' });
  }

  // ✅ DELETE USER
  deleteUser(userId: string): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${userId}`, { responseType: 'text' });
  }

  // ✅ UPDATE ROLE
  updateRole(userId: string, role: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${userId}/role?role=${role}`, null);
  }

  // ✅ GET OWN PROFILE
  getProfile(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/profile`);
  }

  // ✅ UPDATE OWN PROFILE
  updateProfile(updates: { name?: string; email?: string; phone?: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/profile`, updates);
  }

  // ✅ RESET PASSWORD
  resetPassword(userId: string, newPassword: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/reset?userId=${userId}&newPassword=${newPassword}`, null, { responseType: 'text' });
  }
}
