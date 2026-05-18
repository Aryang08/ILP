import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouterOutlet, Routes } from '@angular/router';
import { Component, inject } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { authInterceptor } from './app/core/interceptors/auth.interceptor';
import { errorInterceptor } from './app/core/interceptors/error.interceptor';
import { AuthGuard } from './app/core/guards/auth.guard';
import { RoleGuard } from './app/core/guards/role.guard';
import { AuthService } from './app/core/services/auth.service';
import { LoginPageComponent } from './app/pages/login/login.component';
import { DashboardComponent } from './app/pages/customer-dashboard/dashboard.component';
import { AdminDashboardComponent } from './app/pages/admin-dashboard/dashboard.component';
import { SupervisorDashboardComponent } from './app/pages/supervisor-dashboard/dashboard.component';
import { TechnicianDashboardComponent } from './app/pages/technician-dashboard/dashboard.component';
import { ProfileComponent } from './app/pages/profile/profile.component';
import { AdminUsersComponent } from './app/pages/admin-users/admin-users.component';

@Component({selector:'app-root',standalone:true,imports:[RouterOutlet],template:`<router-outlet/>`})
class AppComponent{}

const routes: Routes = [
  { path:'login', component: LoginPageComponent },
  { path:'', canActivate:[AuthGuard], children:[
    { path:'admin', component: AdminDashboardComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'admin/users', component: AdminUsersComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'customer', component: DashboardComponent, canActivate:[RoleGuard], data:{roles:['CUSTOMER']} },
    { path:'supervisor', component: SupervisorDashboardComponent, canActivate:[RoleGuard], data:{roles:['SUPERVISOR']} },
    { path:'technician', component: TechnicianDashboardComponent, canActivate:[RoleGuard], data:{roles:['TECHNICIAN']} },
    { path:'profile', component: ProfileComponent },
    { path:'', pathMatch:'full', redirectTo:'profile' }
  ]},
  { path:'**', redirectTo:'login' }
];

bootstrapApplication(AppComponent,{providers:[provideRouter(routes),provideHttpClient(withInterceptors([authInterceptor,errorInterceptor])),FormsModule]});
