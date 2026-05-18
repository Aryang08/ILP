import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouterOutlet, Routes } from '@angular/router';
import { Component } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './app/core/interceptors/auth.interceptor';
import { errorInterceptor } from './app/core/interceptors/error.interceptor';
import { AuthGuard } from './app/core/guards/auth.guard';
import { RoleGuard } from './app/core/guards/role.guard';
import { LoginPageComponent } from './app/pages/login/login.component';
import { DashboardComponent } from './app/pages/customer-dashboard/dashboard.component';
import { AdminDashboardComponent } from './app/pages/admin-dashboard/dashboard.component';
import { SupervisorDashboardComponent } from './app/pages/supervisor-dashboard/dashboard.component';
import { TechnicianDashboardComponent } from './app/pages/technician-dashboard/dashboard.component';
import { ProfileComponent } from './app/pages/profile/profile.component';
import { AdminUsersComponent } from './app/pages/admin-users/admin-users.component';

import { SupervisorServicesComponent } from './app/pages/supervisor-services/supervisor-services.component';
import { SupervisorTechnicianAllocationComponent } from './app/pages/supervisor-technician-allocation/supervisor-technician-allocation.component';
import { SupervisorRegisterTechnicianComponent } from './app/pages/supervisor-register-technician/supervisor-register-technician.component';
import { CustomerServiceRequestComponent } from './app/pages/customer-service-request/customer-service-request.component';
import { CustomerServiceHistoryComponent } from './app/pages/customer-service-history/customer-service-history.component';
import { CustomerServiceDetailComponent } from './app/pages/customer-service-detail/customer-service-detail.component';
import { TechnicianRequestListComponent } from './app/pages/technician-request-list/technician-request-list.component';
import { TechnicianJobDetailComponent } from './app/pages/technician-job-detail/technician-job-detail.component';
import { AdminFeedbackComponent } from './app/pages/admin-feedback/admin-feedback.component';
import { AdminSalesComponent } from './app/pages/admin-sales/admin-sales.component';

@Component({selector:'app-root',standalone:true,imports:[RouterOutlet],template:`<router-outlet/>`})
class AppComponent{}

const routes: Routes = [
  { path:'login', component: LoginPageComponent },
  { path:'', canActivate:[AuthGuard], children:[
    { path:'admin', component: AdminDashboardComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'admin/users', component: AdminUsersComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'admin/feedback', component: AdminFeedbackComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'admin/sales', component: AdminSalesComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
    { path:'customer', component: DashboardComponent, canActivate:[RoleGuard], data:{roles:['CUSTOMER']} },
    { path:'customer/request', component: CustomerServiceRequestComponent, canActivate:[RoleGuard], data:{roles:['CUSTOMER']} },
    { path:'customer/history', component: CustomerServiceHistoryComponent, canActivate:[RoleGuard], data:{roles:['CUSTOMER']} },
    { path:'customer/service-detail', component: CustomerServiceDetailComponent, canActivate:[RoleGuard], data:{roles:['CUSTOMER']} },
    { path:'supervisor', component: SupervisorDashboardComponent, canActivate:[RoleGuard], data:{roles:['SUPERVISOR']} },
    { path:'supervisor/services', component: SupervisorServicesComponent, canActivate:[RoleGuard], data:{roles:['SUPERVISOR']} },
    { path:'supervisor/allocation', component: SupervisorTechnicianAllocationComponent, canActivate:[RoleGuard], data:{roles:['SUPERVISOR']} },
    { path:'supervisor/register-technician', component: SupervisorRegisterTechnicianComponent, canActivate:[RoleGuard], data:{roles:['SUPERVISOR']} },
    { path:'technician', component: TechnicianDashboardComponent, canActivate:[RoleGuard], data:{roles:['TECHNICIAN']} },
    { path:'technician/requests', component: TechnicianRequestListComponent, canActivate:[RoleGuard], data:{roles:['TECHNICIAN']} },
    { path:'technician/job-detail', component: TechnicianJobDetailComponent, canActivate:[RoleGuard], data:{roles:['TECHNICIAN']} },
    { path:'profile', component: ProfileComponent },
    { path:'', pathMatch:'full', redirectTo:'profile' }
  ]},
  { path:'**', redirectTo:'login' }
];

bootstrapApplication(AppComponent,{providers:[provideRouter(routes),provideHttpClient(withInterceptors([authInterceptor,errorInterceptor]))]});
