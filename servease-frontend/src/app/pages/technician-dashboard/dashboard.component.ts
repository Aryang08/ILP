import { Component } from '@angular/core';import { RouterLink } from '@angular/router';
@Component({standalone:true,imports:[RouterLink],template:`<nav><a routerLink='/technician'>Technician</a><a routerLink='/profile'>Profile</a></nav><div class='container grid'><div class='card'><h3>Technician Dashboard</h3><p>Assigned jobs and updates table.</p></div></div>`})
export class TechnicianDashboardComponent{}
